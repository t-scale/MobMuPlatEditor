package com.iglesiaintermedia.MobMuPlatEditor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import com.iglesiaintermedia.MobMuPlatEditor.DocumentModel.CanvasType;

public class CanvasPanel extends JPanel implements MouseListener{
	Color bgColor;
	MMPController editingDelegate;
	JLabel buttonBlankLabel;
	Image iconImage;
	
	int pageCount;
	int pageViewIndex;
	CanvasType canvasType;
	boolean isOrientationLandscape;
	Color guideColor;
	
	/*Timer animateTimer;// = new Timer(m_interval, new TimerAction());
	int timerCount;
	int oldX;*/
	
	public CanvasPanel(){
		super();
		canvasType=CanvasType.canvasTypeIPhone3p5Inch;
		pageCount=1;
        pageViewIndex=0;
       guideColor = new Color(0f,0f,0f,.2f);
       this.setBgColor(new Color(127,127,127 ));
       this.addMouseListener(this); 
       /*animateTimer = new Timer(50, new ActionListener() {
    	      public void actionPerformed(ActionEvent e) {
    	          System.out.print("hi");
    	        }
    	      });*/
 
		try{
       URL iconImageURL = CanvasPanel.class.getResource("infoicon_100x100.png");
		 iconImage = ImageIO.read(iconImageURL);
		Image resizedImage =  iconImage.getScaledInstance(30,30, Image.SCALE_SMOOTH);
		 buttonBlankLabel = new JLabel(new ImageIcon( resizedImage ));
		buttonBlankLabel.setBounds(10,10,30,30);
		add( buttonBlankLabel );
		}
		catch(IOException e){}
		
	}
	
	public void setBgColor(Color inBgColor){
		bgColor = inBgColor;
		setBackground(bgColor);
	}
	
	public void setPageViewIndex(int inIndex){
	    pageViewIndex=inIndex;
	    int offset=0;
	    switch(canvasType){
	        case canvasTypeIPhone3p5Inch:
	            offset = (isOrientationLandscape ? 480:320);
	            break;
	        case canvasTypeIPhone4Inch:
	            offset = (isOrientationLandscape ? 568:320);
	            break;
	        case canvasTypeIPad:
	            offset = (isOrientationLandscape ? 1024:768);
	            break;
	    }
	    //slide to new offset to show the page of controls
	   // [NSAnimationContext beginGrouping];
	   // [[NSAnimationContext currentContext] setDuration:0.3f]; // However long you want the slide to take
	    
	   // [[self animator] setFrameOrigin:CGPointMake(-1.0*offset*_pageViewIndex, 0)];
	    this.setLocation(new Point((int)(-1.0*offset*pageViewIndex),0));
	    //[NSAnimationContext endGrouping];
/*	    timerCount=0;
	    oldX=getX();
	    animateTimer.start();*/
	    
	}

	void refreshIcon(CanvasType intype){
		Image resizedImage;
		 switch(intype){
	        case canvasTypeIPhone3p5Inch:
	        	 resizedImage =  iconImage.getScaledInstance(30,30, Image.SCALE_SMOOTH);
	    		// System.out.print("\nresizedImage !=null ? "+(resizedImage!=null));
	        	 buttonBlankLabel.setIcon(new ImageIcon( resizedImage ));
	    		buttonBlankLabel.setBounds(10,10,30,30);
	    		break;
	        case canvasTypeIPhone4Inch:
	        	 resizedImage =  iconImage.getScaledInstance(30,30, Image.SCALE_SMOOTH);
	    		 buttonBlankLabel.setIcon(new ImageIcon( resizedImage ));
	    		buttonBlankLabel.setBounds(10,10,30,30);
	    		break;
	        case canvasTypeIPad:
	        	 resizedImage =  iconImage.getScaledInstance(40,40, Image.SCALE_SMOOTH);
	    		 buttonBlankLabel.setIcon(new ImageIcon( resizedImage ));
	    		buttonBlankLabel.setBounds(20,20,40,40);
	    		break;
		 }
	}
	
	void refresh(){//called on changing pagecount, canvas, orientation
	    int width=0; int height=0;
	    switch(canvasType){
	        case canvasTypeIPhone3p5Inch:
	            width = (isOrientationLandscape ? 480:320);
	            height = (isOrientationLandscape ? 320:480);
	            
	            
	//            [buttonBlankView setFrame:CGRectMake(10, 10, 30, 30)];
	            break;
	        case canvasTypeIPhone4Inch:
	            width = (isOrientationLandscape ? 568:320);
	            height = (isOrientationLandscape ? 320:568);
	//             [buttonBlankView setFrame:CGRectMake(10, 10, 30, 30)];
	            break;
	        case canvasTypeIPad:
	            width = (isOrientationLandscape ? 1024:768);
	            height = (isOrientationLandscape ? 768:1024);
	//             [buttonBlankView setFrame:CGRectMake(20, 20, 40, 40)];
	            break;
	    }
	    this.setBounds(new Rectangle(0,0,width*pageCount, height));//[self setFrame:CGRectMake(0, 0, width*_pageCount, height)];
	    this.setPageViewIndex(pageViewIndex);//[self setPageViewIndex:_pageViewIndex];
	    //System.out.print("\ncanvaswidth"+ this.getWidth()+" pagecount "+pageCount);
	    refreshIcon(canvasType);
	}

	public void setPageCount(int inPageCount){
	    pageCount=inPageCount;
	    refresh();
	}

	public void setCanvasType(CanvasType inCanvasType){
	    canvasType=inCanvasType;
	    refresh();
	}

	public void setIsOrientationLandscape(boolean inIsOrientationLandscape){
	    isOrientationLandscape=inIsOrientationLandscape;
	    refresh();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//this.requestFocus();
		editingDelegate.canvasClicked();
		
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void paintComponent (Graphics g){
		
	    super.paintComponent(g);
	    if (!editingDelegate.snapToGridEnabled) return;
	    g.setColor(guideColor);
	    
	    int width = getWidth();
	    int height = getHeight();
	    for (int i = 0; i <width; i+=editingDelegate.snapToGridXVal) {
	    	g.drawLine(i,0,i,height);
	    }
	    for (int i = 0; i <height; i+=editingDelegate.snapToGridYVal) {
	    	g.drawLine(0,i,width,i);
	    }
	     
	}
	
}