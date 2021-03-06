package eu.isas.peptideshaker.gui;

import eu.isas.peptideshaker.gui.exportdialogs.MzIdentMLExportDialog;

/**
 * A dialog for displaying the various save/export options.
 *
 * @author Harald Barsnes
 */
public class SaveDialog extends javax.swing.JDialog {

    /**
     * The PeptideShaker parent frame.
     */
    private PeptideShakerGUI peptideShakerGUI;

    /**
     * Create a new SaveDialog.
     *
     * @param peptideShakerGUI the dialog parent
     * @param modal modal or not modal
     */
    public SaveDialog(PeptideShakerGUI peptideShakerGUI, boolean modal) {
        super(peptideShakerGUI, modal);
        this.peptideShakerGUI = peptideShakerGUI;
        initComponents();
        setLocationRelativeTo(peptideShakerGUI);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundPanel = new javax.swing.JPanel();
        saveJButton = new javax.swing.JButton();
        exportJButton = new javax.swing.JButton();
        exportPrideJButton = new javax.swing.JButton();
        saveAsJButton = new javax.swing.JButton();
        saveLabel = new javax.swing.JLabel();
        saveAsLabel = new javax.swing.JLabel();
        exportZipLabel = new javax.swing.JLabel();
        exportPrideLabel = new javax.swing.JLabel();

        setTitle("Save & Export");
        setResizable(false);

        backgroundPanel.setBackground(new java.awt.Color(255, 255, 255));

        saveJButton.setFont(saveJButton.getFont().deriveFont(saveJButton.getFont().getStyle() | java.awt.Font.BOLD, saveJButton.getFont().getSize()+3));
        saveJButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        saveJButton.setText("Save Project");
        saveJButton.setToolTipText("Save the PeptideShaker project locally.");
        saveJButton.setFocusPainted(false);
        saveJButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        saveJButton.setIconTextGap(27);
        saveJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveJButtonActionPerformed(evt);
            }
        });

        exportJButton.setFont(exportJButton.getFont().deriveFont(exportJButton.getFont().getStyle() | java.awt.Font.BOLD, exportJButton.getFont().getSize()+3));
        exportJButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/export.png"))); // NOI18N
        exportJButton.setText("Export Project");
        exportJButton.setToolTipText("<html>\nExport the PeptideShaker project as a<br>\nzip file to open on another computer.\n</html>");
        exportJButton.setFocusPainted(false);
        exportJButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        exportJButton.setIconTextGap(27);
        exportJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportJButtonActionPerformed(evt);
            }
        });

        exportPrideJButton.setFont(exportPrideJButton.getFont().deriveFont(exportPrideJButton.getFont().getStyle() | java.awt.Font.BOLD, exportPrideJButton.getFont().getSize()+3));
        exportPrideJButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/export_pride.png"))); // NOI18N
        exportPrideJButton.setText("Export to PRIDE");
        exportPrideJButton.setToolTipText("Export the PeptideShaker project as mzIdentML.");
        exportPrideJButton.setFocusPainted(false);
        exportPrideJButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        exportPrideJButton.setIconTextGap(25);
        exportPrideJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportPrideJButtonActionPerformed(evt);
            }
        });

        saveAsJButton.setFont(saveAsJButton.getFont().deriveFont(saveAsJButton.getFont().getStyle() | java.awt.Font.BOLD, saveAsJButton.getFont().getSize()+3));
        saveAsJButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save_as.png"))); // NOI18N
        saveAsJButton.setText("Save Project As...");
        saveAsJButton.setToolTipText("<html>\nSave the PeptideShaker project<br>\nlocally under a different name.\n</html>");
        saveAsJButton.setFocusPainted(false);
        saveAsJButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        saveAsJButton.setIconTextGap(27);
        saveAsJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsJButtonActionPerformed(evt);
            }
        });

        saveLabel.setFont(saveLabel.getFont().deriveFont((saveLabel.getFont().getStyle() | java.awt.Font.ITALIC) | java.awt.Font.BOLD, saveLabel.getFont().getSize()+1));
        saveLabel.setText("Save the project locally");

        saveAsLabel.setFont(saveAsLabel.getFont().deriveFont((saveAsLabel.getFont().getStyle() | java.awt.Font.ITALIC) | java.awt.Font.BOLD, saveAsLabel.getFont().getSize()+1));
        saveAsLabel.setText("Save the project under a new name");

        exportZipLabel.setFont(exportZipLabel.getFont().deriveFont((exportZipLabel.getFont().getStyle() | java.awt.Font.ITALIC) | java.awt.Font.BOLD, exportZipLabel.getFont().getSize()+1));
        exportZipLabel.setText("Export the project as a zip file");

        exportPrideLabel.setFont(exportPrideLabel.getFont().deriveFont((exportPrideLabel.getFont().getStyle() | java.awt.Font.ITALIC) | java.awt.Font.BOLD, exportPrideLabel.getFont().getSize()+1));
        exportPrideLabel.setText("Export the project as mzIdentML");

        javax.swing.GroupLayout backgroundPanelLayout = new javax.swing.GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        backgroundPanelLayout.setHorizontalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(saveAsJButton, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                    .addComponent(saveJButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exportJButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exportPrideJButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(45, 45, 45)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveAsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exportZipLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exportPrideLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        backgroundPanelLayout.setVerticalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveAsJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveAsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exportJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exportZipLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exportPrideJButton)
                    .addComponent(exportPrideLabel))
                .addGap(25, 25, 25))
        );

        backgroundPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {exportJButton, exportPrideJButton, saveJButton});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Saves the project.
     *
     * @param evt
     */
    private void saveJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveJButtonActionPerformed
        this.setVisible(false);
        peptideShakerGUI.saveProject(false, false);
        this.dispose();
    }//GEN-LAST:event_saveJButtonActionPerformed

    /**
     * Export the dataset as a zip file.
     *
     * @param evt
     */
    private void exportJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportJButtonActionPerformed
        this.dispose();
        peptideShakerGUI.exportProjectAsZip();
    }//GEN-LAST:event_exportJButtonActionPerformed

    /**
     * Open the PRIDE Export dialog.
     *
     * @param evt
     */
    private void exportPrideJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportPrideJButtonActionPerformed
        dispose();
        new MzIdentMLExportDialog(peptideShakerGUI, true);
    }//GEN-LAST:event_exportPrideJButtonActionPerformed

    /**
     * Save the project.
     * 
     * @param evt 
     */
    private void saveAsJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsJButtonActionPerformed
        this.setVisible(false);
        peptideShakerGUI.saveProjectAs(false, false);
        this.dispose();
    }//GEN-LAST:event_saveAsJButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JButton exportJButton;
    private javax.swing.JButton exportPrideJButton;
    private javax.swing.JLabel exportPrideLabel;
    private javax.swing.JLabel exportZipLabel;
    private javax.swing.JButton saveAsJButton;
    private javax.swing.JLabel saveAsLabel;
    private javax.swing.JButton saveJButton;
    private javax.swing.JLabel saveLabel;
    // End of variables declaration//GEN-END:variables
}