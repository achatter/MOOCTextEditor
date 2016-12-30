package spelling;

import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    
    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		String lowerCase = word.toLowerCase();
		if (!isWord(lowerCase)){
			//if word does not exist in dictionary add it
			//set the root as the current node
			//loop through the char array from the string
			//find node equivalent to char
			//if node is empty or the char at that index is not in the node
			//add character to node
			//if node exists, make current node equal to that node
			TrieNode currentNode = root;
			char[] wordChars = lowerCase.toCharArray();
			for(int i=0;i < wordChars.length; i++){
				TrieNode tempNode = currentNode.getChild(wordChars[i]);
				if (tempNode == null){
					tempNode = currentNode.insert(wordChars[i]);
					currentNode = tempNode;
					if(tempNode.endsWord()){
						size++;
					 }
					}
				else{
					currentNode = tempNode;
					}
				}
			if(!currentNode.endsWord()){
				currentNode.setEndsWord(true);
				size++;
				return true;
			}
		}
		return false;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
	    return size;
	}
	
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	public boolean isWord(String s) 
	{
	    TrieNode currentNode = root;
	    String lowerCase = s.toLowerCase();
	    char[] wordChars = lowerCase.toCharArray();
	    
	   
	    for(int i = 0; i < wordChars.length; i++){
	    	TrieNode tmpNode = currentNode.getChild(wordChars[i]);
	    	if (tmpNode == null){
	    		return false;
	    	}
	    	else if(lowerCase.equals(tmpNode.getText()) && tmpNode.endsWord()){
		    	return true;
		    }
	    	else {
	    		currentNode = tmpNode;
	    	}
	    }
		return false;
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 
    	 List<String>predictionList = new LinkedList<String>();
    	 int listCount = 0;
    	 TrieNode thisNode = root;
    	 String lowerCasePrefix = prefix.toLowerCase();
    	 char[] prefixChar = lowerCasePrefix.toCharArray();
    	 
    	 //find the node which has the prefix characters
    	 for(int i = 0; i < prefixChar.length; i++)
    	 {
    		 if(thisNode.getValidNextCharacters().contains(prefixChar[i])){
    			 thisNode = thisNode.getChild(prefixChar[i]);
    		 }
    		 else{
    			 //didn't find any node equal to the prefixchars
    			 //so return an empty list
    			 return predictionList;
    		 }
    		 //if prefix itself is a word, return that
    		 if(thisNode.endsWord()){
    			 listCount = 1;
    			 predictionList.add(thisNode.getText());
    		 }
    	 }

    	 //With the node found for the stem, add the stem to the back of the LinkedList
    	 //and traverse breadth wise to find related words
  //  	 predictionList.add(thisNode.getText());
    	 
    	 List<TrieNode> nodesToSearch = new LinkedList<TrieNode>();
    	 List<Character> charList = new LinkedList<Character>(thisNode.getValidNextCharacters());
    	 
    	 //add all children of current node to the list
    	 for(int i = 0; i < charList.size(); i++){
    		 nodesToSearch.add(thisNode.getChild(charList.get(i)));
    	 }
    	 
    	 while(nodesToSearch!= null && nodesToSearch.size() > 0 && listCount < numCompletions){
    		 
    		 TrieNode trieNode = nodesToSearch.remove(0);
             if (trieNode.endsWord()) 
             {
                 listCount++;
                 predictionList.add(trieNode.getText());
             }

             List<Character> subTrieNodeChildren = new LinkedList<Character>(trieNode.getValidNextCharacters());
             //Adding all next level tries to the linked list - recursive
             for (int i=0; i<subTrieNodeChildren.size();i++) 
             {
                 nodesToSearch.add(trieNode.getChild(subTrieNodeChildren.get(i)));
             }
         }
         return predictionList;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}