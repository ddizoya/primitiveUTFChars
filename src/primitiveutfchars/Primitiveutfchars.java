package primitiveutfchars;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.plaf.synth.SynthSpinnerUI;

public class Primitiveutfchars {

	DataInputStream dis;
	DataOutputStream dos;
	String ruta = "C:\\Users\\David\\Desktop\\texto3.txt";
	char[] frase;
	int tamañoLinea;
	File file = new File(ruta);

	public void escritura(String str) {
		try {
			if (!file.exists())
				file.createNewFile();

			dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(ruta)));
			dis = new DataInputStream(new BufferedInputStream(new FileInputStream(ruta)));
			tamañoLinea = str.length();
			
			//Escribimos y leemos en UTF
			dos.writeUTF(str);
			dos.flush();
			int tamaño = dos.size();
			
			System.out.println("writeUTF ha escrito: " + dis.readUTF());
			System.out.println("writeUTF ha escrito " + tamaño + " bytes.");
			tamaño = dos.size(); //Aquí no haría falta volver a hacer esto, pero sí en los demás.
			
			//Escribimos y leemos en Char
			dos.writeChars(str);
			dos.flush();
			tamaño = dos.size() - tamaño;
			
			frase = new char[str.length()];
			int count = 0;
			while (dis.available() > 0) {
				frase[count] = dis.readChar();
				count++;
			}
			
			System.out.println("writeChars ha escrito: " + String.copyValueOf(frase));
			System.out.println("writeChars ha escrito: " + tamaño);
			tamaño = dos.size(); //Debemos volver a asignar el tamaño total grabado, que es de 56 bytes. Si no hacemos esto guarda sólo 36.
		
			
			//Escribimos y leemos en UTF de nuevo
			dos.writeUTF(str);
			dos.flush();
			tamaño = dos.size() - tamaño;
			
			System.out.println("writeUTF ha escrito: " + dis.readUTF());
			System.out.println("writeUTF ha escrito " + tamaño+" bytes.");
			tamaño = dos.size();
			
			System.out.println("Bytes totales escritos: " +tamaño);
			
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				dis.close();
				dos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void lectura() {

		try {

			dis = new DataInputStream(new BufferedInputStream(new FileInputStream(ruta)));
			int total, restante, leido;
			total = dis.available();
		
			System.out.println("Leemos la primera cadena en UTF: " + dis.readUTF());
			
			restante = dis.available();
			leido = total - restante;
			
			System.out.println("Número de bytes leidos: " + leido);
			
			total = dis.available();
			leido= 0;
			restante = 0;
			int i = 0;
			frase = new char[tamañoLinea];
			while (i < tamañoLinea) {
				frase[i] = dis.readChar();
				i++;
			}

			System.out.println("Leemos la segunda cadena Chars : " + String.copyValueOf(frase));
			restante = dis.available();
			leido = total - restante;
			System.out.println("El número de bytes leídos es: " + leido);
			System.out.println("Bytes por leer: " + dis.available());
	
			System.out.println("Leemos la tercera línea mediante UTF: " + dis.readUTF());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				dis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Primitiveutfchars obj = new Primitiveutfchars();
		obj.escritura("Esto es una cadena");
		obj.lectura();
	}

}
