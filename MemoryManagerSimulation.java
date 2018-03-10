package memorymanagementsim;

import memorymanagementsim.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.EventListener;

public class MemoryManagerSimulation extends JPanel {

	private static void createAndShowGUI() {
        //Create and set up the window.
		MemoryManager memoryMngr = new MemoryManager();
		memoryMngr.initializeData();


		//memoryMngr.placement(1);

        JFrame frame = new JFrame("Memory Management Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        TabbedPane tpd = new TabbedPane();

        //Add content to the window.

        // frame.add(new TextDemo());

        //Display the window.
        // frame.pack();
        // frame.setVisible(true);

        frame.add(tpd, BorderLayout.CENTER);
        // //Display the window.
        frame.pack();
        // frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setVisible(true);

        ArrayList finishedSimulations = new ArrayList<Integer>();

        while (true) {
        	int previousSelectedTab = 0;
        	int currentSelectedTab = tpd.tabbedPane.getSelectedIndex();
        	System.out.print("");

        	if (previousSelectedTab != currentSelectedTab && !finishedSimulations.contains(currentSelectedTab)) {


                memoryMngr.MemoryList = new ArrayList<MemoryBlock>();
                memoryMngr.JobList = new ArrayList<Job>();

                memoryMngr.initializeData();

        		finishedSimulations.add(currentSelectedTab);
        		previousSelectedTab = currentSelectedTab;

		        for (int timerCtr = 1; !memoryMngr.areAllJobsAllocated(); timerCtr++) {
		            // System.out.println(tpd.tabbedPane.getSelectedIndex());

		            if (tpd.tabbedPane.getSelectedIndex() == 1) {

		                JComponent newPanel = tpd.makeTextPanel(new Integer(timerCtr).toString(), memoryMngr.MemoryList, memoryMngr.JobList);
		                 tpd.tabbedPane.setComponentAt(1, newPanel);
		                 frame.pack();
		                // tpd.tabbedPane.getTabComponentAt(1).repaint();
		                // System.out.println(tpd.tabbedPane.getComponentAt(1));
		                memoryMngr.sortBlocks(1);
		               
		                memoryMngr.placement(1);
		                memoryMngr.printProgress();
		            }

		            if (tpd.tabbedPane.getSelectedIndex() == 2) {
		                // tpd.tabbedPane.getTabComponentAt(1).repaint();
		                // System.out.println(tpd.tabbedPane.getComponentAt(1));

		                JComponent newPanel = tpd.makeTextPanel(new Integer(timerCtr).toString(), memoryMngr.MemoryList, memoryMngr.JobList);
		                tpd.tabbedPane.setComponentAt(2, newPanel);

		                frame.pack();

                        memoryMngr.sortBlocks(2);
                       
                        memoryMngr.placement(2);
                        memoryMngr.printProgress();
		            }

                    if (tpd.tabbedPane.getSelectedIndex() == 3) {
                        // tpd.tabbedPane.getTabComponentAt(1).repaint();
                        // System.out.println(tpd.tabbedPane.getComponentAt(1));

                        JComponent newPanel = tpd.makeTextPanel(new Integer(timerCtr).toString(), memoryMngr.MemoryList, memoryMngr.JobList);
                        tpd.tabbedPane.setComponentAt(3, newPanel);

                        frame.pack();

                        memoryMngr.sortBlocks(3);
                       
                        memoryMngr.placement(3);
                        memoryMngr.printProgress();
                    }

		            try {
			            // thread to sleep for 1000 milliseconds
			            Thread.sleep(1000);
			         } catch (Exception e) {
			            System.out.println(e);
			         }
		            
		        }

		       


        	}


        }
       
    }

	public static void main(String[] args) {
		// MemoryManager memoryMngr = new MemoryManager();
		// memoryMngr.initializeData();


		// // memoryMngr.placement(1);
		// memoryMngr.JobList = new ArrayList<Job>();
		// memoryMngr.MemoryList = new ArrayList<MemoryBlock>();

		createAndShowGUI();

	}
}


class TabbedPane extends JPanel{
	 JTabbedPane tabbedPane = new JTabbedPane();

	public TabbedPane() {
        super(new GridLayout(1, 1));

        tabbedPane.setFont( new Font( "Calibri", Font.BOLD|Font.ITALIC, 20 ));
        
        
        ImageIcon icon = createImageIcon("images/pixelcomputer.png");
        
        JComponent panel1 = createEditorPane();
        panel1.setPreferredSize(new Dimension(1150, 1000));
        tabbedPane.addTab("Information", icon, panel1,
                "Information on the Placement Algorithms");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        
        JComponent panel2 = makeTextPanel("");
        panel2.setPreferredSize(new Dimension(1150, 1000));
        tabbedPane.addTab("First-Fit Placement Simulation", icon, panel2,
                "Simulate the First-Fit placement algorithm");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        
        JComponent panel3 = makeTextPanel("");
        panel3.setPreferredSize(new Dimension(1150, 1000));
        tabbedPane.addTab("Best-Fit Placement Simulation", icon, panel3,
                "Simulate the Best-Fit placement algorithm");
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        JComponent panel4 = makeTextPanel("");
        panel4.setPreferredSize(new Dimension(1150, 1000));
        tabbedPane.addTab("Worst-Fit Placement Simulation", icon, panel4,
                "Simulate the Worst-Fit placement algorithm");
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
        
        // JComponent panel4 = makeTextPanel(
        //         "Panel #4 (has a preferred size of 410 x 50).");
        // panel4.setPreferredSize(new Dimension(2000, 800));
        // tabbedPane.addTab("Tab 4", icon, panel4,
        //         "Does nothing at all");
        // tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

        //Add the tabbed pane to this panel.
        add(tabbedPane);

        //The following line enables to use scrolling tabs.
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			// System.out.println(tabbedPane.getSelectedIndex());


   //      // final boolean bIsVisible = tabbedPane.isEnabledAt( nTabIndex );
   //              final int nTabIndex = tabbedPane.indexOfTabComponent( panel2 );

   //      System.out.println(nTabIndex);

   //              System.out.println(tabbedPane.getTabCount());

   //              if (tabbedPane.isEnabledAt(2)) {
   //                  System.out.println("hehe");
   //              }
   //      System.out.println("hell no");

    }


    protected static JComponent makeTextPanel(String text, ArrayList<MemoryBlock> memoryList, ArrayList<Job> jobList) {
        JPanel panel = new JPanel(false);
        //JTextArea filler = new JTextArea(text);
        // filler.setFont( new Font( "Calibri", Font.PLAIN, 22 ) );

        // JTextArea filler2 = new JTextArea(text + text);
        //filler.setHorizontalAlignment(SwingConstants.CENTER);
        DrawRectangle dr = new DrawRectangle();
        dr.timer = text;
        dr.memoryList = memoryList;
        dr.jobList = jobList;

		// System.out.println("yoooooooooo" + jobList.get(0).remainingTime + " we at " + text);
        System.out.println("TIME at: " + text);

        panel.setLayout(new GridLayout(1, 2));

        // dr.s = "hello";
        panel.add(dr);
        //panel.add(filler);
        
        // panel.add(filler2);
        return panel;
    }

    protected static JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JTextArea filler = new JTextArea(text);
        filler.setFont( new Font( "Calibri", Font.PLAIN, 22 ) );

        // JTextArea filler2 = new JTextArea(text + text);
        //filler.setHorizontalAlignment(SwingConstants.CENTER);
        //DrawRectangle dr = new DrawRectangle();

        panel.setLayout(new GridLayout(1, 3));

        // dr.s = "hello";
        //panel.add(dr);
        panel.add(filler);
        
        // panel.add(filler2);
        return panel;
    }
    
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = TabbedPane.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private JEditorPane createEditorPane() {
        System.out.println("yes!!!!!!!!!!");
        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        java.net.URL helpURL = MemoryManagerSimulation.class.getResource(
                                        "TextSamplerDemoHelp.html");
        if (helpURL != null) {
            try {
                editorPane.setPage(helpURL);
            } catch (IOException e) {
                System.err.println("Attempted to read a bad URL: " + helpURL);
            }
        } else {
            System.err.println("Couldn't find file: TextSampleDemoHelp.html");
        }

        // editorPane.addHyperlinkListener(new HyperlinkListener() {
        //     public void hyperlinkUpdate(HyperlinkEvent e) {
        //         if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
        //            // Do something with e.getURL() here
        //         }
        //     }
        // });


        return editorPane;
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from
     * the event dispatch thread.
     */

    
    // public static void main(String[] args) {
    //     //Schedule a job for the event dispatch thread:
    //     //creating and showing this application's GUI.
    //     // SwingUtilities.invokeLater(new Runnable() {
    //     //     public void run() {
    //     //         //Turn off metal's use of bold fonts
    //     // 		UIManager.put("swing.boldMetal", Boolean.FALSE);
    //     // 		createAndShowGUI();
    //     //             }
    //     // });
    //     createAndShowGUI();
       
    // }
}



class DrawRectangle extends JPanel {
	String memoryString = "Memory 1";
	String allocatedJobString = "Job being allocated: ";
	String remainingTimeString = "Remaining Time: ";
	String extraSpaceString = "Extra Space: ";
	String timer = "";
    double percentage;
	ArrayList<MemoryBlock> memoryList;
	ArrayList<Job> jobList;
    MemoryManager mmrymngr = new MemoryManager();

	int totalRectangles = 10;


    private int getOriginalBlockIndex(int index) {
        for (int i = 0; i < memoryList.size(); i++) {
            if (memoryList.get(i).memoryNumber == (index+1)) {
                return i;
            }
        }

        return 0;
    }

	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.setFont(new Font("SansSerif", Font.PLAIN, 14)); 

        int rectX = 50;
        int rectY = 5;

        int memoryStringX = 55;
        int memoryStringY = 25;

        int rectWidth = 350;
        int rectHeight = 95;

        /*************************************************************/

        int jobDetailsRectY = 40;

        int jobDetailsTextY = jobDetailsRectY + 20;

        /*************************************************************/

        int jobNumberRectX = 450; 
        int jobNumberRectY = 175;

        int jobNumberRectWidth = 150;
        int jobNumberRectHeight = 30;

        int jobTextY = jobNumberRectY + 20;
        int jobTextX = 455;

        /************************************************************/


        for (int i = 0; i < totalRectangles; i++) {

            // if (i%2 == 0) {
            //     g.setColor(new Color(230, 230, 250));
            // } else {
            //     g.setColor(new Color(175, 238, 238));
            // }
            
        	// g.fillRect(rectX, rectY, rectWidth, rectHeight);

         //    g.setColor(Color.black);
         //    g.drawRect(rectX, rectY, rectWidth, rectHeight);

        	// memoryStringY = rectY + 20;

        	/* Strings to be displayed */
            g.setColor(Color.black);
        	memoryString = "Memory Block #" + (i+1);

            int originalIndex = getOriginalBlockIndex(i);

        	if (!memoryList.get(originalIndex).jobQueue.isEmpty()) {
        		allocatedJobString = "Allocated Job: Job " + (memoryList.get(originalIndex).jobQueue.get(0)+1);
        		remainingTimeString = "Remaining Time: " + jobList.get(memoryList.get(originalIndex).jobQueue.get(0)).remainingTime + "s";
				extraSpaceString = "Extra Space: " + memoryList.get(originalIndex).internalFragmentation + "K";
                g.setColor(new Color(175, 238, 238));
        	} else {
        		allocatedJobString = "Allocated Job: None";
        		remainingTimeString = "";
				extraSpaceString = "";
                g.setColor(new Color(230, 230, 250));
        	}

            g.fillRect(rectX, rectY, rectWidth, rectHeight);

            g.setColor(Color.black);
            g.drawRect(rectX, rectY, rectWidth, rectHeight);

            memoryStringY = rectY + 20;
        	
            g.setFont(new Font("SansSerif", Font.PLAIN, 14)); 
        	g.drawString(memoryString, memoryStringX, memoryStringY);
        	memoryStringY = memoryStringY + 20;
        	g.drawString(allocatedJobString, memoryStringX, memoryStringY);
        	memoryStringY = memoryStringY + 20;
        	g.drawString(remainingTimeString, memoryStringX, memoryStringY);
        	memoryStringY = memoryStringY + 20;
        	g.drawString(extraSpaceString, memoryStringX, memoryStringY);
        	memoryStringY = memoryStringY + 20;

        	rectY = rectY + rectHeight;
        }

        Color customGreen = new Color(255, 255, 255); // Color white

        g.setFont(new Font("SansSerif", Font.BOLD, 20)); 
        g.setColor(new Color(53, 221, 67));
        g.drawString("TIME ELAPSED: " + timer + "s", 450, 30);

        // g.setFont(new Font("SansSerif", Font.BOLD, 20)); 
        // g.setColor(Color.black);
        // g.drawString(mmrymngr.getPercentage(memoryList, jobList) + " %", jobTextX + 3 * jobNumberRectWidth + 10, jobDetailsTextY);

        for (int i = 0; i < 4; i++) {
            g.setColor(Color.black);
            g.drawRect(jobNumberRectX, jobDetailsRectY, jobNumberRectWidth, jobNumberRectHeight);
            g.drawRect(jobNumberRectX + jobNumberRectWidth, jobDetailsRectY, jobNumberRectWidth + jobNumberRectWidth, jobNumberRectHeight);

            if (i == 0) {
                g.setFont(new Font("SansSerif", Font.BOLD, 14));
                g.drawString("Throughput", jobTextX, jobDetailsTextY);
                g.setFont(new Font("Monospaced", Font.PLAIN, 14));
                g.drawString(mmrymngr.getThroughput(jobList) + " jobs per " + timer + " seconds", jobTextX + jobNumberRectWidth, jobDetailsTextY);
            } else if (i == 1) {
                g.setFont(new Font("SansSerif", Font.BOLD, 14));
                g.drawString("Waiting Queue", jobTextX, jobDetailsTextY);
                g.setFont(new Font("Monospaced", Font.PLAIN, 14));
                g.drawString(mmrymngr.getWaitingQueueLength(jobList) + " jobs waiting", jobTextX + jobNumberRectWidth, jobDetailsTextY);
            } else if (i == 2) {
                g.setFont(new Font("SansSerif", Font.BOLD, 14));
                g.drawString("Waiting Jobs", jobTextX, jobDetailsTextY);
                String waitingJobQueue = "";

                for (int j = 0; j < jobList.size(); j++) {
                    if (jobList.get(j).status == 0 || jobList.get(j).status == 3) {
                        waitingJobQueue = waitingJobQueue + jobList.get(j).jobNumber;

                        if (j != jobList.size()-1) {
                            waitingJobQueue = waitingJobQueue + ", ";
                        }
                    }
                }
                g.setFont(new Font("SansSerif", Font.PLAIN, 12));
                g.drawString(waitingJobQueue, jobTextX + jobNumberRectWidth, jobDetailsTextY);

            } else if (i == 3) {
                g.setFont(new Font("SansSerif", Font.BOLD, 14));
                g.drawString("Storage Usage", jobTextX, jobDetailsTextY);
                g.setFont(new Font("SansSerif", Font.PLAIN, 12));
                g.drawString(mmrymngr.getPercentage(memoryList, jobList) + " % of memory used", jobTextX + jobNumberRectWidth, jobDetailsTextY);

            }


            jobDetailsRectY = jobDetailsRectY + jobNumberRectHeight;
            jobDetailsTextY = jobDetailsTextY + jobNumberRectHeight;

        }



        for (int i = -1; i < jobList.size(); i++) {

            g.setColor(Color.black);
            g.drawRect(jobNumberRectX, jobNumberRectY, jobNumberRectWidth, jobNumberRectHeight);
            g.drawRect(jobNumberRectX + jobNumberRectWidth, jobNumberRectY, jobNumberRectWidth, jobNumberRectHeight); 
            g.drawRect(jobNumberRectX + jobNumberRectWidth + jobNumberRectWidth, jobNumberRectY, jobNumberRectWidth, jobNumberRectHeight); 
            
            if (i == -1) {
                g.setFont(new Font("SansSerif", Font.BOLD, 14));
                g.drawString("Job", jobTextX, jobTextY);
                g.drawString("Status", jobTextX + jobNumberRectWidth, jobTextY);
                g.drawString("Waiting Time", jobTextX + jobNumberRectWidth + jobNumberRectWidth, jobTextY);
            } else {
                g.setFont(new Font("SansSerif", Font.PLAIN, 14));
                g.drawString("Job " + jobList.get(i).jobNumber, jobTextX, jobTextY);
                g.drawString(jobList.get(i).waitingTime + " s", jobTextX + jobNumberRectWidth + jobNumberRectWidth, jobTextY);

                String jobStatusString = "";

                if (jobList.get(i).status == 0 || jobList.get(i).status == 3) {
                    g.setColor(Color.red);
                    jobStatusString = "Unallocated";
                } else if (jobList.get(i).status == 1) {
                    g.setColor(Color.blue);
                    jobStatusString = "Running";
                } else if (jobList.get(i).status == 2) {
                    g.setColor(new Color(53, 221, 67));
                    jobStatusString = "Completed";
                }
                
                g.drawString(jobStatusString, jobTextX + jobNumberRectWidth, jobTextY);
            }

            jobNumberRectY = jobNumberRectY + jobNumberRectHeight;
            jobTextY = jobTextY + jobNumberRectHeight;    

        }

       


        // g.drawRect(100, 10, 350, 85);
        // g.drawString("Memory" + " 1", 105, 25);
        // g.drawString("Job being allocated: ", 105, 45);
        // g.drawString("Remaining Time: ", 105, 65);
        // g.drawString("Extra Space: ", 105, 85);

        // System.out.println(s);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100,100); // As suggested by camickr
    }
}