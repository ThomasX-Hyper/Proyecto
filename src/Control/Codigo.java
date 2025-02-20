package Control;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javax.swing.*;
import javazoom.jl.player.advanced.AdvancedPlayer;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;

public class Codigo {

    public void Video(String url, JPanel panelDestino) {
        JFXPanel jfxpanel=new JFXPanel();
        Platform.runLater(()->{
        String url1=convertToEmbedUrl(url);
        WebView web=new WebView();
        web.setPrefSize(panelDestino.getWidth(), panelDestino.getHeight());
        WebEngine engune=web.getEngine();
        engune.load(url1);
        
        jfxpanel.setScene(new Scene(web,panelDestino.getWidth(), panelDestino.getHeight()));
       panelDestino.removeAll();
        panelDestino.setLayout(new BorderLayout());
        panelDestino.add(jfxpanel,BorderLayout.CENTER);
        panelDestino.revalidate();
        panelDestino.repaint();
        
        });
        
    }
    
    
    private String convertToEmbedUrl(String url) {
    if (url.contains("watch?v=")) {
        return url.replace("watch?v=", "embed/")+ "?autoplay=1";
    }
    return url;
}  
    
    public static void mp3(String url){
     try (InputStream ist=new URL(url).openStream();
        ){
        //URL direc=new URL(url);
        AdvancedPlayer pay=new AdvancedPlayer(ist);
        pay.play();
    }catch (Exception e) {
            System.out.println("Error al reproducir el audio: " + e.getMessage());
            
        }
    }
     
     private Clip clip;
  public void Reproduc(String url,JButton cambio){
     
      try{
      if (clip != null && clip.isRunning()) {
           cambio.setText("Reanudar");
           clip.stop(); 
            return; 
        }
      if(clip !=null && clip.isOpen()){
      cambio.setText("Pausar");
          clip.start();
      return;
      
      }   URL dato=getClass().getResource(url);
          if(dato==null){
         JOptionPane.showMessageDialog(null, "Archivo no encontrado"+url);
          }
          AudioInputStream audio=AudioSystem.getAudioInputStream(dato);
          clip=AudioSystem.getClip();
          clip.open(audio);
          clip.start();
          cambio.setText("Pausar");
          
      }catch (Exception e) {
            System.out.println("Error al reproducir el audio: " + e.getMessage());
        }
       
      }
  
  
  
  public void tecla(String url){
      Clip  clip=null;
      try{
  URL direccion=getClass().getResource(url);
  if(direccion==null){
  JOptionPane.showMessageDialog(null, "Archivo no encontrado"+url );
  }
  AudioInputStream entrada=AudioSystem.getAudioInputStream(direccion);
  clip=AudioSystem.getClip();
  clip.open(entrada);
  clip.start();
  
  
  }catch(Exception e) {
            System.out.println("Error al reproducir el audio: " + e.getMessage());
  }}
  
  
  static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); } // Cargar OpenCV

   
private List<String> capturas=new ArrayList<>();

    public void tomarFoto() {
        VideoCapture camara=new VideoCapture(0,Videoio.CAP_DSHOW);
        if (!camara.isOpened()) {
            JOptionPane.showMessageDialog(null, "No se pudo acceder a la cámara");
            return;
        }
        try {
            Mat frame = new Mat();
              camara.read(frame);
                if (!frame.empty()) {
            String direccion="C:\\Users\\IK\\Desktop\\Sesion11_Ejemplo01_Sin_Codigo\\foto_"+UUID.randomUUID().toString()+".png";
            Imgcodecs.imwrite(direccion, frame); 
            capturas.add(direccion);
            camara.release();
            JOptionPane.showMessageDialog(null, "Foto tomada y guardada como 'foto.png'");
        } else {
            JOptionPane.showMessageDialog(null, "Error al tomar la foto");
        }
        } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
            
        }finally{
        camara.release();
        }
        
        
      

      
    }
    
    public void mostrarImagenesTomadas() {
    if (capturas.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No se han tomado fotos.");
    } else {
        StringBuilder mensaje = new StringBuilder("Imágenes tomadas:\n\n");

        // Crear un panel para las imágenes y sus rutas
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (String ruta : capturas) {
            // Crear el ImageIcon para mostrar la imagen
            ImageIcon imageIcon = new ImageIcon(ruta);

            // Redimensionar la imagen al tamaño deseado (por ejemplo, 200x200 píxeles)
            Image img = imageIcon.getImage();  // Obtener la imagen de ImageIcon
            Image scaledImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);  // Cambiar tamaño
            ImageIcon scaledIcon = new ImageIcon(scaledImg);  // Crear nuevo ImageIcon con la imagen redimensionada

            // Crear un JLabel para mostrar la imagen redimensionada
            JLabel imageLabel = new JLabel(scaledIcon);
 JLabel rutaLabel = new JLabel("Ruta: " + ruta);
            // Agregar la imagen al panel
            panel.add(imageLabel);
            panel.add(Box.createVerticalStrut(20));  // Espacio entre imágenes
        }
        

        // Mostrar las imágenes redimensionadas en un JOptionPane
        JOptionPane.showMessageDialog(null, panel, "Imágenes Tomadas", JOptionPane.INFORMATION_MESSAGE);
    }
}

  public void Mostrarimagenes(){
  StringBuilder mensaje=new StringBuilder();
  JPanel panel=new JPanel();
  panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
  
  for(String ruta:capturas){
  ImageIcon icon=new ImageIcon(ruta);
  
  JLabel imalabel=new JLabel(icon);
  panel.add(imalabel);
  }
  JOptionPane.showMessageDialog(null, panel,"Imagenes tomadas nuevas",JOptionPane.INFORMATION_MESSAGE);
  }
}
