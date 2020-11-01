package com.assets;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class FileSelector {
	File file;
	
	public static File returnFile() {
		Path path = FileSystems.getDefault().getPath(".");
		JFileChooser fc = new JFileChooser();
		FileFilter ff1 = new FileFilter() {
			
			@Override
			public String getDescription() {
				return "png";
			}
			
			@Override
			public boolean accept(File f) {
				String extension = new String("");
				for(int i=f.getName().length()-1; i>0;i--) {
					if(f.getName().charAt(i) == '.') {
						break;
					}
					
					extension = String.valueOf(f.getName().charAt(i)) + extension;
				}
				extension = extension.toLowerCase();
				
				if(extension.equals("png")) {
						return true;
					}
				return false;
			}
		};
		
		FileFilter ff2 = new FileFilter() {
			
			@Override
			public String getDescription() {
				return "jpg";
			}
			
			@Override
			public boolean accept(File f) {
				String extension = new String("");
				for(int i=f.getName().length()-1; i>0;i--) {
					if(f.getName().charAt(i) == '.') {
						break;
					}
					
					extension = String.valueOf(f.getName().charAt(i)) + extension;
				}
				extension = extension.toLowerCase();
				
				if(extension.equals("jpg")) {
						return true;
					}
				return false;
			}
		};
		
		FileFilter ff3 = new FileFilter() {
			
			@Override
			public String getDescription() {
				return "jpeg";
			}
			
			@Override
			public boolean accept(File f) {
				String extension = new String("");
				for(int i=f.getName().length()-1; i>0;i--) {
					if(f.getName().charAt(i) == '.') {
						break;
					}
					
					extension = String.valueOf(f.getName().charAt(i)) + extension;
				}
				extension = extension.toLowerCase();
				
				if(extension.equals("jpeg")) {
						return true;
					}
				return false;
			}
		};
		
		fc.addChoosableFileFilter(ff1);
		fc.addChoosableFileFilter(ff3);
		fc.addChoosableFileFilter(ff2);
		fc.setCurrentDirectory(path.toFile());
		fc.setDialogTitle("Choose maze image to solve !");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile();
		}
		
		 return null;
	}
}
