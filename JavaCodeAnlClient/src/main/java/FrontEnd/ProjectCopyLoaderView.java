/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontEnd;

import Controller.ProjectCopyLoaderController;
import java.io.File;

/**
 *
 * @author jefemayoneso
 */
public class ProjectCopyLoaderView<T> extends javax.swing.JFrame {

    ProjectCopyLoaderController<T> controller;

    /**
     * Creates new form ProjectLoaderView
     */
    public ProjectCopyLoaderView() {
        initComponents();
        initVars();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelContainer = new javax.swing.JPanel();
        jPanelBtnsProjects = new javax.swing.JPanel();
        jPanelProj1 = new javax.swing.JPanel();
        jpanelProject1Title = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanelProject1Section = new javax.swing.JPanel();
        jButtonChooseDir1 = new javax.swing.JButton();
        jLablePath1 = new javax.swing.JLabel();
        jPanelProj2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanelProject2Section = new javax.swing.JPanel();
        jButtonChooseDir2 = new javax.swing.JButton();
        jLabelPath2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButtonAnalyzePjcts = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemOpenProject = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelBtnsProjects.setLayout(new javax.swing.BoxLayout(jPanelBtnsProjects, javax.swing.BoxLayout.LINE_AXIS));

        jpanelProject1Title.setLayout(new java.awt.CardLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("PROYECTO 1");
        jpanelProject1Title.add(jLabel1, "card2");

        jButtonChooseDir1.setText("Elegir");
        jButtonChooseDir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChooseDir1ActionPerformed(evt);
            }
        });
        jPanelProject1Section.add(jButtonChooseDir1);

        jLablePath1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLablePath1.setText("No hay archivo elegido");
        jPanelProject1Section.add(jLablePath1);

        javax.swing.GroupLayout jPanelProj1Layout = new javax.swing.GroupLayout(jPanelProj1);
        jPanelProj1.setLayout(jPanelProj1Layout);
        jPanelProj1Layout.setHorizontalGroup(
            jPanelProj1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpanelProject1Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanelProj1Layout.createSequentialGroup()
                .addComponent(jPanelProject1Section, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanelProj1Layout.setVerticalGroup(
            jPanelProj1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProj1Layout.createSequentialGroup()
                .addComponent(jpanelProject1Title, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelProject1Section, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanelBtnsProjects.add(jPanelProj1);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("PROYECTO 2");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jButtonChooseDir2.setText("Elegir");
        jButtonChooseDir2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChooseDir2ActionPerformed(evt);
            }
        });
        jPanelProject2Section.add(jButtonChooseDir2);

        jLabelPath2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPath2.setText("No hay archivo elegido");
        jPanelProject2Section.add(jLabelPath2);

        javax.swing.GroupLayout jPanelProj2Layout = new javax.swing.GroupLayout(jPanelProj2);
        jPanelProj2.setLayout(jPanelProj2Layout);
        jPanelProj2Layout.setHorizontalGroup(
            jPanelProj2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProj2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelProj2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanelProject2Section, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelProj2Layout.setVerticalGroup(
            jPanelProj2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProj2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelProject2Section, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanelBtnsProjects.add(jPanelProj2);

        jPanel1.setLayout(new java.awt.CardLayout());

        jButtonAnalyzePjcts.setText("ANALIZAR");
        jButtonAnalyzePjcts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnalyzePjctsActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonAnalyzePjcts, "card2");

        javax.swing.GroupLayout jPanelContainerLayout = new javax.swing.GroupLayout(jPanelContainer);
        jPanelContainer.setLayout(jPanelContainerLayout);
        jPanelContainerLayout.setHorizontalGroup(
            jPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBtnsProjects, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelContainerLayout.setVerticalGroup(
            jPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContainerLayout.createSequentialGroup()
                .addComponent(jPanelBtnsProjects, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jMenu1.setText("Archivo");

        jMenuItemOpenProject.setText("Abrir");
        jMenuItemOpenProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOpenProjectActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemOpenProject);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemOpenProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemOpenProjectActionPerformed
        File copyFile = this.controller.getCopyFile();
        if (copyFile != null) {
            ProjectEditorView pev = new ProjectEditorView(copyFile);
            pev.setLocationRelativeTo(null);
            pev.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            pev.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItemOpenProjectActionPerformed

    private void jButtonChooseDir1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonChooseDir1ActionPerformed
        this.controller.savePath1();
        String show = this.controller.getPath1();
        show = show != null && show.length() > 23 ? String.format("...%1$s", show.substring(show.length() - 20, show.length())) : show;
        this.jLablePath1.setText(show);
    }// GEN-LAST:event_jButtonChooseDir1ActionPerformed

    private void jButtonChooseDir2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonChooseDir2ActionPerformed
        this.controller.savePath2();
        String show = this.controller.getPath2();
        show = show != null && show.length() > 23 ? String.format("...%1$s", show.substring(show.length() - 20, show.length())) : show;
        this.jLabelPath2.setText(show);
    }// GEN-LAST:event_jButtonChooseDir2ActionPerformed

    private void jButtonAnalyzePjctsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonAnalyzePjctsActionPerformed
        if (!this.controller.analyzeFiles()) {
            ErrorShowerView tableErrors = new ErrorShowerView(this.controller.getErrors());
            tableErrors.setVisible(true);
            tableErrors.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            tableErrors.setLocationRelativeTo(null);
        }
    }// GEN-LAST:event_jButtonAnalyzePjctsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAnalyzePjcts;
    private javax.swing.JButton jButtonChooseDir1;
    private javax.swing.JButton jButtonChooseDir2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelPath2;
    private javax.swing.JLabel jLablePath1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemOpenProject;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelBtnsProjects;
    private javax.swing.JPanel jPanelContainer;
    private javax.swing.JPanel jPanelProj1;
    private javax.swing.JPanel jPanelProj2;
    private javax.swing.JPanel jPanelProject1Section;
    private javax.swing.JPanel jPanelProject2Section;
    private javax.swing.JPanel jpanelProject1Title;
    // End of variables declaration//GEN-END:variables

    private void initVars() {
        this.controller = new ProjectCopyLoaderController<>();
    }

}
