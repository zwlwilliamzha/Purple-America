package com.purple.america;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class White {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DrawFrame frame = new DrawFrame(args[0]);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}

class DrawFrame extends JFrame {
	public DrawFrame(String arg) {
		this.setTitle("Draw White USA");
		this.setSize(1000, 800);

		DrawPanel panel = new DrawPanel();
		panel.setParameter(arg);
		this.add(panel);
	}
}

class DrawPanel extends JPanel {

	private String _fileName;

	public void setParameter(String fileName) {
		_fileName = fileName;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ArrayList<ArrayList<String>> data = readTxtFile(System
				.getProperty("user.dir")
				+ File.separator
				+ "geoData"
				+ File.separator + _fileName + ".txt");
		if (data != null) {
			double d_start_x = 0;
			double d_start_y = 0;
			double d_end_x = 0;
			double d_end_y = 0;
			int scale = 15;
			for (int i = 0; i < data.size(); i++) {
				if (i == 0) {
					// get the start X/Y and end X/Y
					String[] start = data.get(0).get(0).split("   ");
					d_start_x = new Double(start[0].trim());
					d_start_y = new Double(start[1].trim());

					String[] end = data.get(0).get(1).split("   ");
					d_end_x = new Double(end[0].trim());
					d_end_y = new Double(end[1].trim());

					double width = d_end_x - d_start_x;
					double height = d_end_y - d_start_y;
					// estimate the scale to zoom in the map
					int scaleX = (int) Math.floor(1000 / width);
					int scaleY = (int) Math.floor(800 / height);
					if (scaleX <= scaleY) {
						scale = scaleX - 2;
					} else
						scale = scaleY - 25;

				}

				else {
					Polygon po = new Polygon();

					for (int j = 0; j < data.get(i).size(); j++) {
						if (j > 2) {
							// offset X and Y from start point
							String[] point = data.get(i).get(j).split("   ");
							int pointX = (int) Math.floor((Double
									.valueOf(point[0].trim()) - d_start_x)
									* scale + 10);
							// the real world Y is opposite with screen Y
							int pointY = (int) Math.floor((d_end_y - Double
									.valueOf(point[1].trim())) * scale + 10);
							po.addPoint(pointX, pointY);
						}
					}

					g.setColor(Color.BLACK);
					g.drawPolygon(po);
				}
			}

			g.dispose();
		}
	}

	/**
	 * Read Geography data from geoData
	 * 
	 * @param filePath
	 * @return
	 */
	private ArrayList<ArrayList<String>> readTxtFile(String filePath) {
		try {
			ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
			ArrayList<String> blockData = new ArrayList<String>();
			File file = new File(filePath);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file));
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				int j = 0;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if ("".equals(lineTxt)) {
						j = 0;
						data.add(blockData);
						blockData = new ArrayList<String>();

					} else {
						blockData.add(lineTxt);
					}

					j++;

				}
				read.close();
				return data;

			} else {
				System.out.println("The file named " + _fileName
						+ ".txt is not found.");
				return null;
			}
		} catch (Exception e) {
			System.out.println("Read file failure.");
			e.printStackTrace();
			return null;
		}

	}

}
