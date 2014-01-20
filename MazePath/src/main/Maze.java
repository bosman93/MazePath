package main;

import graphic.MainFrame;

import java.awt.EventQueue;


public class Maze{
	public static void main(String[] args)
	{		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainFrame();
			}
		});
	}
}
