package eu.isas.peptideshaker.export.section_generators;

import com.compomics.util.experiment.biology.Peptide;
import com.compomics.util.experiment.identification.Identification;
import com.compomics.util.experiment.identification.SearchParameters;
import com.compomics.util.experiment.identification.matches.ModificationMatch;
import com.compomics.util.experiment.identification.matches.SpectrumMatch;
import com.compomics.util.experiment.massspectrometry.Precursor;
import com.compomics.util.experiment.massspectrometry.Spectrum;
import com.compomics.util.experiment.massspectrometry.SpectrumFactory;
import com.compomics.util.gui.waiting.waitinghandlers.ProgressDialogX;
import eu.isas.peptideshaker.export.ExportFeature;
import eu.isas.peptideshaker.export.exportfeatures.PsmFeatures;
import eu.isas.peptideshaker.myparameters.PSParameter;
import eu.isas.peptideshaker.myparameters.PSPtmScores;
import eu.isas.peptideshaker.scoring.PtmScoring;
import eu.isas.peptideshaker.utils.IdentificationFeaturesGenerator;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshallerException;

/**
 * This class outputs the PSM related export features.
 *
 * @author Marc Vaudel
 */
public class PsmSection {

    /**
     * The spectrum factory.
     */
    private SpectrumFactory spectrumFactory = SpectrumFactory.getInstance();
    /**
     * The features to export.
     */
    private ArrayList<ExportFeature> exportFeatures;
    /**
     * The separator used to separate columns.
     */
    private String separator;
    /**
     * Boolean indicating whether the line shall be indexed.
     */
    private boolean indexes;
    /**
     * Boolean indicating whether column headers shall be included.
     */
    private boolean header;
    /**
     * The writer used to send the output to file.
     */
    private BufferedWriter writer;

    /**
     * Constructor.
     *
     * @param exportFeatures the features to export in this section
     * @param separator
     * @param indexes
     * @param header
     * @param writer
     */
    public PsmSection(ArrayList<ExportFeature> exportFeatures, String separator, boolean indexes, boolean header, BufferedWriter writer) {
        this.exportFeatures = exportFeatures;
        this.separator = separator;
        this.indexes = indexes;
        this.header = header;
        this.writer = writer;
    }

    /**
     * Writes the desired section.
     *
     * @param identification the identification of the project
     * @param identificationFeaturesGenerator the identification features
     * generator of the project
     * @param searchParameters the search parameters of the project
     * @param keys the keys of the protein matches to output
     * @param progressDialog the progress dialog
     * @throws IOException exception thrown whenever an error occurred while
     * writing the file.
     * @throws IllegalArgumentException
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws InterruptedException
     * @throws MzMLUnmarshallerException
     */
    public void writeSection(Identification identification, IdentificationFeaturesGenerator identificationFeaturesGenerator,
            SearchParameters searchParameters, ArrayList<String> keys, ProgressDialogX progressDialog) throws IOException, IllegalArgumentException, SQLException,
            ClassNotFoundException, InterruptedException, MzMLUnmarshallerException {

        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Exporting PSM Details. Please Wait...");
        
        if (header) {
            if (indexes) {
                writer.write(separator);
            }
            boolean firstColumn = true;
            for (ExportFeature exportFeature : exportFeatures) {
                if (firstColumn) {
                    firstColumn = false;
                } else {
                    writer.write(separator);
                }
                writer.write(exportFeature.getTitle());
            }
            writer.newLine();
        }
        
        HashMap<String, ArrayList<String>> psmMap = new HashMap<String, ArrayList<String>>();
        
        if (keys == null) {
            psmMap = identification.getSpectrumIdentificationMap();
        } else {
            for (String key : keys) {
                String fileName = Spectrum.getSpectrumFile(key);
                if (!psmMap.containsKey(fileName)) {
                    psmMap.put(fileName, new ArrayList<String>());
                }
                psmMap.get(fileName).add(key);
            }
        }
        
        PSParameter psParameter = new PSParameter();
        SpectrumMatch spectrumMatch = null;
        String matchKey = "", parameterKey = "";
        int line = 1;
        
        int totalSize = 0;
        
        for (String spectrumFile : psmMap.keySet()) {
            totalSize += psmMap.get(spectrumFile).size();
        }
        
        progressDialog.setIndeterminate(false);
        progressDialog.setValue(0);
        progressDialog.setMaxProgressValue(totalSize);
        
        for (String spectrumFile : psmMap.keySet()) {

            for (String spectrumKey : psmMap.get(spectrumFile)) {
                
                progressDialog.increaseProgressValue();
                
                if (indexes) {
                    writer.write(line + separator);
                }
                for (ExportFeature exportFeature : exportFeatures) {
                    PsmFeatures psmFeature = (PsmFeatures) exportFeature;
                    switch (psmFeature) {
                        case ptms:
                            if (!matchKey.equals(spectrumKey)) {
                                spectrumMatch = identification.getSpectrumMatch(spectrumKey);
                                matchKey = spectrumKey;
                            }
                            HashMap<String, ArrayList<Integer>> modMap = getModMap(spectrumMatch.getBestAssumption().getPeptide());
                            ArrayList<String> modList = new ArrayList<String>(modMap.keySet());
                            Collections.sort(modList);
                            String output = "";
                            for (String mod : modList) {
                                if (!output.equals("")) {
                                    output += ", ";
                                }
                                output += mod;
                                if (modMap.get(mod).size() > 1) {
                                    output += " (" + modMap.get(mod).size() + ")";
                                }
                            }
                            writer.write(separator);
                            break;
                        case a_score:
                            if (!matchKey.equals(spectrumKey)) {
                                spectrumMatch = identification.getSpectrumMatch(spectrumKey);
                                matchKey = spectrumKey;
                            }
                            modList = new ArrayList<String>(getModMap(spectrumMatch.getBestAssumption().getPeptide()).keySet());
                            Collections.sort(modList);
                            PSPtmScores ptmScores = new PSPtmScores();
                            output = "";
                            for (String mod : modList) {
                                if (spectrumMatch.getUrParam(ptmScores) != null) {
                                    if (!output.equals("")) {
                                        output += ", ";
                                    }
                                    ptmScores = (PSPtmScores) spectrumMatch.getUrParam(new PSPtmScores());
                                    output += mod + " (";
                                    if (ptmScores != null && ptmScores.getPtmScoring(mod) != null) {
                                        String location = ptmScores.getPtmScoring(mod).getBestAScoreLocations();
                                        if (location != null) {
                                            ArrayList<Integer> locations = PtmScoring.getLocations(location);
                                            Collections.sort(locations);
                                            String commaSeparated = "";
                                            for (int aa : locations) {
                                                if (!commaSeparated.equals("")) {
                                                    commaSeparated += ", ";
                                                }
                                                commaSeparated += aa;
                                            }
                                            output += commaSeparated + ": ";
                                            Double aScore = ptmScores.getPtmScoring(mod).getAScore(location);
                                            output += aScore + "";
                                        } else {
                                            output += "Not Scored";
                                        }
                                    } else {
                                        output += "Not Scored";
                                    }
                                    output += ")";
                                }
                            }
                            writer.write(output + separator);
                            break;
                        case d_score:
                            if (!matchKey.equals(spectrumKey)) {
                                spectrumMatch = identification.getSpectrumMatch(spectrumKey);
                                matchKey = spectrumKey;
                            }
                            modList = new ArrayList<String>(getModMap(spectrumMatch.getBestAssumption().getPeptide()).keySet());
                            Collections.sort(modList);
                            ptmScores = new PSPtmScores();
                            output = "";
                            for (String mod : modList) {
                                if (spectrumMatch.getUrParam(ptmScores) != null) {
                                    if (!output.equals("")) {
                                        output += ", ";
                                    }
                                    ptmScores = (PSPtmScores) spectrumMatch.getUrParam(new PSPtmScores());
                                    output += mod + " (";
                                    if (ptmScores != null && ptmScores.getPtmScoring(mod) != null) {
                                        String location = ptmScores.getPtmScoring(mod).getBestDeltaScoreLocations();
                                        if (location != null) {
                                            ArrayList<Integer> locations = PtmScoring.getLocations(location);
                                            Collections.sort(locations);
                                            String commaSeparated = "";
                                            for (int aa : locations) {
                                                if (!commaSeparated.equals("")) {
                                                    commaSeparated += ", ";
                                                }
                                                commaSeparated += aa;
                                            }
                                            output += commaSeparated + ": ";
                                            Double dScore = ptmScores.getPtmScoring(mod).getDeltaScore(location);
                                            output += dScore + "";
                                        } else {
                                            output += "Not Scored";
                                        }
                                    } else {
                                        output += "Not Scored";
                                    }
                                    output += ")";
                                }
                            }
                            writer.write(output + separator);
                            break;
                        case accessions:
                            if (!matchKey.equals(spectrumKey)) {
                                spectrumMatch = identification.getSpectrumMatch(spectrumKey);
                                matchKey = spectrumKey;
                            }
                            output = "";
                            for (String accession : spectrumMatch.getBestAssumption().getPeptide().getParentProteins()) {
                                if (!output.equals("")) {
                                    output += ", ";
                                }
                                output += accession;
                            }
                            writer.write(output + separator);
                            break;
                        case confidence:
                            if (!parameterKey.equals(spectrumKey)) {
                                psParameter = (PSParameter) identification.getSpectrumMatchParameter(spectrumKey, psParameter);
                                parameterKey = spectrumKey;
                            }
                            writer.write(psParameter.getPsmConfidence() + separator);
                            break;
                        case decoy:
                            if (!matchKey.equals(spectrumKey)) {
                                spectrumMatch = identification.getSpectrumMatch(spectrumKey);
                                matchKey = spectrumKey;
                            }
                            if (spectrumMatch.getBestAssumption().isDecoy()) {
                                writer.write(1 + separator);
                            } else {
                                writer.write(0 + separator);
                            }
                            break;
                        case hidden:
                            if (!parameterKey.equals(spectrumKey)) {
                                psParameter = (PSParameter) identification.getSpectrumMatchParameter(spectrumKey, psParameter);
                                parameterKey = spectrumKey;
                            }
                            if (psParameter.isHidden()) {
                                writer.write(1 + separator);
                            } else {
                                writer.write(0 + separator);
                            }
                            break;
                        case identification_charge:
                            if (!matchKey.equals(spectrumKey)) {
                                spectrumMatch = identification.getSpectrumMatch(spectrumKey);
                                matchKey = spectrumKey;
                            }
                            writer.write(spectrumMatch.getBestAssumption().getIdentificationCharge().toString() + separator);
                            break;
                        case isotope:
                            if (!matchKey.equals(spectrumKey)) {
                                spectrumMatch = identification.getSpectrumMatch(spectrumKey);
                                matchKey = spectrumKey;
                            }
                            Precursor precursor = spectrumFactory.getPrecursor(spectrumKey);
                            writer.write(spectrumMatch.getBestAssumption().getIsotopeNumber(precursor.getMz()) + separator);
                            break;
                        case localization_confidence:
                            if (!matchKey.equals(spectrumKey)) {
                                spectrumMatch = identification.getSpectrumMatch(spectrumKey);
                                matchKey = spectrumKey;
                            }
                            modList = new ArrayList<String>(getModMap(spectrumMatch.getBestAssumption().getPeptide()).keySet());
                            Collections.sort(modList);
                            ptmScores = new PSPtmScores();
                            output = "";
                            for (String mod : modList) {
                                if (spectrumMatch.getUrParam(ptmScores) != null) {
                                    if (!output.equals("")) {
                                        output += ", ";
                                    }

                                    ptmScores = (PSPtmScores) spectrumMatch.getUrParam(new PSPtmScores());
                                    output += " (";

                                    if (ptmScores != null && ptmScores.getPtmScoring(mod) != null) {

                                        int ptmConfidence = ptmScores.getPtmScoring(mod).getPtmSiteConfidence();

                                        if (ptmConfidence == PtmScoring.NOT_FOUND) {
                                            output += "Not Scored";
                                        } else if (ptmConfidence == PtmScoring.RANDOM) {
                                            output += "Random";
                                        } else if (ptmConfidence == PtmScoring.DOUBTFUL) {
                                            output += "Doubtfull";
                                        } else if (ptmConfidence == PtmScoring.CONFIDENT) {
                                            output += "Confident";
                                        } else if (ptmConfidence == PtmScoring.VERY_CONFIDENT) {
                                            output += "Very Confident";
                                        }
                                    } else {
                                        output += "Not Scored";
                                    }

                                    output += ")";
                                }
                            }
                            writer.write(output + separator);
                            break;
                        case mz:
                            precursor = spectrumFactory.getPrecursor(spectrumKey);
                            writer.write(precursor.getMz() + separator);
                            break;
                        case mz_error:
                            if (!matchKey.equals(spectrumKey)) {
                                spectrumMatch = identification.getSpectrumMatch(spectrumKey);
                                matchKey = spectrumKey;
                            }
                            precursor = spectrumFactory.getPrecursor(spectrumKey);
                            writer.write(spectrumMatch.getBestAssumption().getDeltaMass(precursor.getMz(), true) + separator);
                            break;
                        case rt:
                            precursor = spectrumFactory.getPrecursor(spectrumKey);
                            writer.write(precursor.getRt() + separator);
                            break;
                        case score:
                            if (!parameterKey.equals(spectrumKey)) {
                                psParameter = (PSParameter) identification.getSpectrumMatchParameter(spectrumKey, psParameter);
                                parameterKey = spectrumKey;
                            }
                            writer.write(psParameter.getPsmProbabilityScore() + separator);
                            break;
                        case sequence:
                            if (!matchKey.equals(spectrumKey)) {
                                spectrumMatch = identification.getSpectrumMatch(spectrumKey);
                                matchKey = spectrumKey;
                            }
                            writer.write(spectrumMatch.getBestAssumption().getPeptide().getSequence() + separator);
                            break;
                        case spectrum_charge:
                            precursor = spectrumFactory.getPrecursor(spectrumKey);
                            writer.write(precursor.getPossibleChargesAsString() + separator);
                            break;
                        case spectrum_file:
                            writer.write(spectrumFile + separator);
                            break;
                        case spectrum_number:
                            writer.write(spectrumFactory.getSpectrum(spectrumKey).getScanNumber() + separator);
                            break;
                        case spectrum_title:
                            writer.write(Spectrum.getSpectrumTitle(spectrumKey) + separator);
                            break;
                        case starred:
                            if (!parameterKey.equals(spectrumKey)) {
                                psParameter = (PSParameter) identification.getSpectrumMatchParameter(spectrumKey, psParameter);
                                parameterKey = spectrumKey;
                            }
                            if (psParameter.isStarred()) {
                                writer.write(1 + separator);
                            } else {
                                writer.write(0 + separator);
                            }
                            break;
                        case theoretical_mass:
                            if (!matchKey.equals(spectrumKey)) {
                                spectrumMatch = identification.getSpectrumMatch(spectrumKey);
                                matchKey = spectrumKey;
                            }
                            writer.write(spectrumMatch.getBestAssumption().getPeptide().getMass() + separator);
                            break;
                        case validated:
                            if (!parameterKey.equals(spectrumKey)) {
                                psParameter = (PSParameter) identification.getSpectrumMatchParameter(spectrumKey, psParameter);
                                parameterKey = spectrumKey;
                            }
                            if (psParameter.isValidated()) {
                                writer.write(1 + separator);
                            } else {
                                writer.write(0 + separator);
                            }
                            break;
                        default:
                            writer.write("Not implemented" + separator);
                    }
                }
                writer.newLine();
                line++;
            }
        }
    }

    /**
     * Returns a map of the modifications in a peptide. Modification name ->
     * sites.
     *
     * @param peptide
     * @return the map of the modifications on a peptide sequence
     */
    private HashMap<String, ArrayList<Integer>> getModMap(Peptide peptide) {
        HashMap<String, ArrayList<Integer>> modMap = new HashMap<String, ArrayList<Integer>>();

        for (ModificationMatch modificationMatch : peptide.getModificationMatches()) {
            if (modificationMatch.isVariable()) {
                if (!modMap.containsKey(modificationMatch.getTheoreticPtm())) {
                    modMap.put(modificationMatch.getTheoreticPtm(), new ArrayList<Integer>());
                }
                modMap.get(modificationMatch.getTheoreticPtm()).add(modificationMatch.getModificationSite());
            }
        }

        return modMap;
    }
}
