package dropeya;
import javax.swing.JFileChooser;
import java.io.*;
import java.net.*;

public class DropEyA extends javax.swing.JFrame {

    public DropEyA() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        BtnSubir = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BtnSubir.setText("Subir Archivos");
        BtnSubir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSubirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 867, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(BtnSubir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(51, 51, 51))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BtnSubir, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void EnviarArchivo(File f, String host, int pto){
        //Socket cliente para enviare muchos archivos a la vez
        try
        {
            String nombre = f.getName();
            long tam = f.length();
            String path = f.getAbsolutePath();

            Socket cl = new Socket(host, pto);
            DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //OutputStream

            System.out.println("\nSe envia el archivo " + path + " con " + tam + " bytes");
            DataInputStream dis = new DataInputStream(new FileInputStream(path)); // InputStream

            dos.writeUTF(nombre);
            dos.flush();
            dos.writeLong(tam);
            dos.flush();

            long enviados = 0;
            int n = 0, porciento = 0;
            byte[] b = new byte[2000];

            while(enviados < tam)
            {
                n = dis.read(b);
                dos.write(b, 0, n);
                dos.flush();
                enviados += n;
                porciento = (int)((enviados * 100) / tam);
                System.out.println("\r Enviando el " + porciento + "% --- " + enviados + "/" + tam + " bytes");
            }//while
            
            dis.close();
            dos.close();
            cl.close();
            System.out.println("Archivo " + nombre + " enviado.");
            
        }catch(Exception e){
            e.printStackTrace();
        }//catch
    }
    private void BtnSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSubirActionPerformed

        JFileChooser jf = new JFileChooser();
        jf.setMultiSelectionEnabled(true);
        //Permite seleccionar carpetas y archivos
        jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int r = jf.showOpenDialog(null);

        if(r == JFileChooser.APPROVE_OPTION)
        {
            File[] files = jf.getSelectedFiles();

            int pto = 4321;
            String host = "127.0.0.1";
            int numArchivos = files.length;

            System.out.println("\nSe envian " + numArchivos + " archivos");

            for(File f : files)
            {
                EnviarArchivo(f, host, pto);
            }//for	
        }//if
    }//GEN-LAST:event_BtnSubirActionPerformed


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DropEyA().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnSubir;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
