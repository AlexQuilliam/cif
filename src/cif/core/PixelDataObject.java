package cif.core;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class PixelDataObject {
	private int[][] pixelData = null;
	
	//creates an object holding an image's pixeldata
	public PixelDataObject(BufferedImage image) {
		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	    final int width = image.getWidth();
	    final int height = image.getHeight();
	    final boolean hasAlphaChannel = image.getAlphaRaster() != null;
	    pixelData = new int[height][width];
	    
	    if(hasAlphaChannel) {
	    	final int pixelLength = 4;
	        	for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
	        		int argb = 0;
	        		argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
	            
	        		argb += ((int) pixels[pixel + 1] & 0xff); // blue
	        		argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
	        		argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
	        		pixelData[row][col] = argb;
	        		col++;
	        		
	        		if (col == width) {
	        			col = 0;
	        			row++;
	        		}
	        	}
	        	
	      }else {
	    	  final int pixelLength = 3;
	    	  
	    	  for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
	    		  int argb = 0;
	    		  argb += -16777216; // 255 alpha
	    		  argb += ((int) pixels[pixel] & 0xff); // blue
	    		  argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
	    		  argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
	    		  pixelData[row][col] = argb;
	    		  col++;
	    		  
	    		  if (col == width) {
	    			  col = 0;
	    			  row++;
	    		  }
	    	  }
	      }
	}
	
	public int[][] getPixelData() {
		return pixelData;
	}
}
