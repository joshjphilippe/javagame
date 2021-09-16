package handlers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JOptionPane;

import main.Main;
import player.Player;

/**
 * 
 * @author Joshua Jean-Philippe
 * Serialize the player file for writing and reading
 * Serializing this information allows us to save it and retrieve it later
 */
public class FileHandler implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final static String savedir = "./data/saves/";
	private static Player p = new Player("null", 10, 5, 10);

	/**
	 * Create dummy player object
	 * Overwrite the name with desired character name
	 * Save/Write the new player file
	 * 
	 * Current Player params: name hp atk gold
	 * 
	 * Catching IOException since we're handling input/output of a file
	 * We need to "catch" any errors that may occur during this process
	 * 
	 * TODO:
	 * Player Inventory Creation
	 */
	public static void createPlayer() {
		String name = JOptionPane.showInputDialog("Enter Character Name:");
		if(name.isEmpty()) {
			System.out.println("Null entry\t\tNo File Created");
		} else {
			p.setName(name);
			InventoryHandler.createInventory(p);
			try {
				FileOutputStream fileOut = new FileOutputStream(savedir + p.getName()+".ser");//We save using .ser because we are serializing the player
				ObjectOutputStream objOut = new ObjectOutputStream(fileOut);//This lets us write the player object to their save file
				objOut.writeObject(p);
				objOut.close();//Closing the object stream since we don't need it anymore
				System.out.println("Player: "+ p.getName() + ", has been created.");
			} catch(IOException ioe) {
				System.out.println("There was an error in creating player file. \n"
						+ "Check file path for possible issue!");
			}			
		}

	}

	/**
	 * We'll make a dummy player object
	 * Populate the dummy with our player's stats
	 * 
	 * Create an array for the files in a savedir
	 * Populate the array with the info
	 * We'll then loop through the array and print out each file
	 * 
	 * Current Player params: name hp atk gold
	 * 
	 * Catching IOException again because we're handling input/output of files
	 * 
	 * TODO:
	 * Player Inventory Loading
	 */
	public static void loadPlayer() {		
		/**
		 * Prompt character name and being loading process
		 * FileInputStream to import player file from savedir
		 * ObjectInputStream to import the data from the file into our Dummy player object
		 * And manipulate it with our Player Constructor
		 */
		String name = JOptionPane.showInputDialog("Enter Character Name:");
		try {
			FileInputStream fis = new FileInputStream(savedir + name + ".ser");
			ObjectInputStream ois = new ObjectInputStream(fis);//This will import the players file from the FileInputStream
			p = (Player) ois.readObject();//This is going to take our dummy player object and read the data from our ObjectInputStream and create the player from the player constructor
			ois.close();//Close ois since we're done loading player
			Main.mainScreen(p);
			Main.addMessage("\nPlayer: ["+ p.getName() +"] has been loaded!\nNow Displaying Player Information..\n");
			Main.addMessage(p.getStats()+"\n\n");
			InventoryHandler.loadInventory(p);
		} catch(IOException | ClassNotFoundException ioe) {
			System.out.println("Error loading player, improper name? Save directory missing?");
		}
	}
	
	/**
	 * Create another dummy player object
	 * Get current player information
	 * Populate the dummy object with our current players information
	 * Save/Output the file :)
	 * 
	 * Current Player params: name hp atk gold
	 * 
	 * We are using IOException because we are handling the transference of files!!!
	 * 
	 * @param p = current Player object/file being used by game
	 * 
	 * TODO:
	 * Save inventory!
	 * 
	 */
	public static void savePlayer(Player p) {
		Player x = new Player("save", 0, 0, 0);
		
		System.out.println("\nRetrieving Player's Information..");
		Utils.delay(1);
		
		String pName = p.getName();
		int pHp = p.getHp();
		int pAtk = p.getAtk();
		int pGold = p.getGold();
		
		x.setName(pName);
		x.setHp(pHp);
		x.setAtk(pAtk);
		x.setGold(pGold);
		
		InventoryHandler.saveInventory(p);
		
		System.out.println("\nSaving Player's Information..");
		Utils.delay(1);
		
		try {
			FileOutputStream fos = new FileOutputStream(savedir + p.getName() + ".ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(p);
			oos.close();
			System.out.println(p.getName()+", has been saved!\n");
		} catch(IOException ioe) {
			System.out.println("Couldn't save the data! File path missing?");
		}
	}
	



}
