import com.company.Buy;
import com.company.Session;
import com.company.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class LogAnalyzer
{
      //constants to be used when pulling data out of input
      //leave these here and refer to them to pull out values
   private static final String START_TAG = "START";
   private static final int START_NUM_FIELDS = 3;
   private static final int START_SESSION_ID = 1;
   private static final int START_CUSTOMER_ID = 2;
   private static final String BUY_TAG = "BUY";
   private static final int BUY_NUM_FIELDS = 5;
   private static final int BUY_SESSION_ID = 1;
   private static final int BUY_PRODUCT_ID = 2;
   private static final int BUY_PRICE = 3;
   private static final int BUY_QUANTITY = 4;
   private static final String VIEW_TAG = "VIEW";
   private static final int VIEW_NUM_FIELDS = 4;
   private static final int VIEW_SESSION_ID = 1;
   private static final int VIEW_PRODUCT_ID = 2;
   private static final int VIEW_PRICE = 3;
   private static final String END_TAG = "END";
   private static final int END_NUM_FIELDS = 2;
   private static final int END_SESSION_ID = 1;

      //a good example of what you will need to do next
      //creates a map of sessions to customer ids
   private static void processStartEntry(
      final String[] words,
      final Map<String, List<String>> sessionsFromCustomer)
   {
      if (words.length != START_NUM_FIELDS)
      {
         return;
      }

      //check if there already is a list entry in the map
         //for this customer, if not create one
      List<String> sessions = sessionsFromCustomer
         .get(words[START_CUSTOMER_ID]);
      if (sessions == null)
      {
         sessions = new LinkedList<>();
         sessionsFromCustomer.put(words[START_CUSTOMER_ID], sessions);
      }

         //now that we know there is a list, add the current session
      sessions.add(words[START_SESSION_ID]);
   }

      //similar to processStartEntry, should store relevant view
      //data in a map - model on processStartEntry, but store
      //your data to represent a view in the map (not a list of strings)
   private static void processViewEntry(
           final String[] words,
           final Map<String, List<View>> viewsFromSession
      /* add parameters as needed */
      )
   {
      List<View> viewSessions = viewsFromSession.get(words[VIEW_SESSION_ID]);
      if(viewSessions == null) {
         viewSessions = new ArrayList<View>();
         viewsFromSession.put(words[VIEW_SESSION_ID], viewSessions);
      }

      viewSessions.add(new View(words[VIEW_SESSION_ID],
      words[VIEW_PRODUCT_ID], words[VIEW_PRICE]));

   }

      //similar to processStartEntry, should store relevant purchases
      //data in a map - model on processStartEntry, but store
      //your data to represent a purchase in the map (not a list of strings)
   private static void processBuyEntry(
      final String[] words,
      final Map<String, List<Buy>> buysFromSession
      /* add parameters as needed */
      )
   {

      List<Buy> buySessions = buysFromSession.get(words[BUY_SESSION_ID]);
      if(buySessions == null) {
         buySessions = new ArrayList<Buy>();
         buysFromSession.put(words[BUY_SESSION_ID], buySessions);
      }

      buySessions.add(new Buy(words[BUY_PRODUCT_ID], words[BUY_PRICE], words[BUY_QUANTITY]));
   }

   private static void processEndEntry(final String[] words)
   {
      if (words.length != END_NUM_FIELDS)
      {
         return;
      }
   }

      //this is called by processFile below - its main purpose is
      //to process the data using the methods you write above
   private static void processLine(
      final String line,
      final Map<String, List<String>> sessionsFromCustomer,
      final Map<String, List<View>> viewsFromSession,
      final Map<String, List<Buy>> buysFromSession
      /* add parameters as needed */
      )
   {
      final String[] words = line.split("\\h");

      if (words.length == 0)
      {
         return;
      }

      switch (words[0])
      {
         case START_TAG:
            processStartEntry(words, sessionsFromCustomer);
            break;
         case VIEW_TAG:
            processViewEntry(words, viewsFromSession /* add arguments as needed */ );
            break;
         case BUY_TAG:
            processBuyEntry(words, buysFromSession /* add arguments as needed */ );
            break;
         case END_TAG:
            processEndEntry(words /* add arguments as needed */ );
            break;
      }
   }

      //write this after you have figured out how to store your data
      //make sure that you understand the problem
      private static void printSessionPriceDifference(final Map<String, List<View>> viewsFromSession,
                                                      final Map<String, List<Buy>> buysFromSession) {

         System.out.println("Price Difference for Purchased Product by Session");
         for (Map.Entry<String, List<Buy>> entry : buysFromSession.entrySet()) {

            //printing each season
            System.out.println(entry.getKey());

            //getting the value or list of buys from the ith key and value
            List<Buy> buyList = entry.getValue();

            for (Buy bought : buyList) {
               //printing out product associated with the season id
               System.out.print("\t\t" + bought.getProductID() + " ");

               //views corresponding to sesion
               List<View> viewList = viewsFromSession.get(entry.getKey());

               double totalPriceForSession = 0.0;
               double count = 0.0;
               double avg = 0.0;


               for (View viewCounter : viewList) {
                  totalPriceForSession = totalPriceForSession + viewCounter.getViewPrice();

                  count++;
               }
               avg = totalPriceForSession / count;

               System.out.printf("%.2f\n", bought.getProductPrice() - avg);
            }

         }


         /* add printing */
      }



   //write this after you have figured out how to store your data
   //make sure that you understand the problem
   private static void printCustomerItemViewsForPurchase(

           final Map<String, List<String>> sessionsFromCustomer,
           final Map<String, List<View>> viewsFromSession,
           final Map<String, List<Buy>> buysFromSession) {

      System.out.println("Number of Views for Purchased Product by Customer");

      for (Map.Entry<String, List<String>> entry : sessionsFromCustomer.entrySet()) {

         boolean printed = false;

         //getting sesions corresponding to ith customer
         List<String> sessions = entry.getValue();

         for (String sessionID : sessions) {

            List<Buy> buySession = buysFromSession.get(sessionID);

            if (buySession != null) {

               if (printed == false) {
                  //print out customer
                  System.out.println(entry.getKey());

                  printed = true;
               }

               for (Buy thisBuy : buySession) {

                  int count = 0;

                  System.out.print("\t" + thisBuy.getProductID());

                  //kinda a double for loop for sessions
                  for (String thisSession : sessions) {
                     List<View> theViews = viewsFromSession.get(thisSession);
                     if (theViews != null) {
                        for (View thisView : theViews) {

                           if (thisView.getProduct().equals(thisBuy.getProductID())) {
                              count++;
                              break;
                           }
                        }

                     }
                  }

                  System.out.println("\t" + count);


               }

            }
         }
      }

   }

   private static void printCustomerAverageItemViewsForNoPurchase(

           final Map<String, List<View>> viewsFromSession,
           final Map<String, List<Buy>> buysFromSession) {


      int count = 0;
      int sessionNoPurchase = 0;
      //print average num of items viewd by vistor(defined by id) that dont make a purchase
      for (Map.Entry<String, List<View>> entry : viewsFromSession.entrySet()) {

         List<Buy> theBuys = buysFromSession.get(entry.getKey());
         List<View> theViews = entry.getValue();


         if (theBuys == null) {
            sessionNoPurchase++;

            for (View thisView : theViews) {

               count++;
            }
         }
      }
      double average = ((double) count) / sessionNoPurchase;

      System.out.println("Average Views Without Purchase: " + average);

   }




   //write this after you have figured out how to store your data
      //make sure that you understand the problem
   private static void printStatistics(
           final Map<String, List<String>> sessionsFromCustomer,
           final Map<String, List<View>> viewsFromSession,
           final Map<String, List<Buy>> buysFromSession)
      /* add parameters as needed */

   {

      printCustomerAverageItemViewsForNoPurchase(viewsFromSession, buysFromSession);
      System.out.println();
      printSessionPriceDifference(viewsFromSession, buysFromSession);
      System.out.println();

      printCustomerItemViewsForPurchase
              (sessionsFromCustomer,viewsFromSession,buysFromSession
                      /*add arguments as needed */);

      /* This is commented out as it will not work until you read
         in your data to appropriate data structures, but is included
         to help guide your work - it is an example of printing the
         data once propogated
      printOutExample(sessionsFromCustomer, viewsFromSession, buysFromSession);
       */

   }

   /* provided as an example of a method that might traverse your
      collections of data once they are written 
      commented out as the classes do not exist yet - write them! */

   private static void printOutExample(
      final Map<String, List<String>> sessionsFromCustomer,
      final Map<String, List<View>> viewsFromSession,
      final Map<String, List<Buy>> buysFromSession) 
   {
      //for each customer, get their sessions
      //for each session compute views
      for(Map.Entry<String, List<String>> entry: 
         sessionsFromCustomer.entrySet()) 
      {
         System.out.println(entry.getKey());
         List<String> sessions = entry.getValue();
         for(String sessionID : sessions)
         {
            System.out.println("\tin " + sessionID);
            if(viewsFromSession.get(sessionID) != null) {
               List<View> theViews = viewsFromSession.get(sessionID);
               for (View thisView : theViews) {
                  System.out.println("\t\tlooked at " + thisView.getProduct());
               }
            }
         }
      }
   }


      //called in populateDataStructures
   private static void processFile(
      final Scanner input,
      final Map<String, List<String>> sessionsFromCustomer,
      final Map<String, List<View>> viewsFromSession,
      final Map<String, List<Buy>> buysFromSession
      /* add parameters as needed */
      )
   {
      while (input.hasNextLine())
      {
         processLine(input.nextLine(), sessionsFromCustomer,viewsFromSession,buysFromSession
            /* add arguments as needed */ );
      }
   }

      //called from main - mostly just pass through important data structures
   private static void populateDataStructures(
      final String filename,
      final Map<String, List<String>> sessionsFromCustomer,
      final Map<String, List<View>> viewsFromSession,
      final Map<String, List<Buy>> buysFromSession
      /* add parameters as needed */
      )
      throws FileNotFoundException
   {
      try (Scanner input = new Scanner(new File(filename)))
      {
         processFile(input, sessionsFromCustomer,viewsFromSession,buysFromSession
            /* add arguments as needed */ );
      }
   }

   private static String getFilename(String fileName)
   {
      System.out.println("FILE NAME: " + fileName);
      return fileName;
   }

   public static void main(String[] args)
   {
      /* Map from a customer id to a list of session ids associated with
       * that customer.
       */
      final Map<String, List<String>> sessionsFromCustomer = new HashMap<>();
      final Map<String, List<View>> viewsFromSession = new HashMap<>();
      final Map<String, List<Buy>> buysFromSession = new HashMap<>();

      /* create additional data structures to hold relevant information */
      /* they will most likely be maps to important data in the logs */

      final String filename = getFilename(args[0]);

      try
      {
         populateDataStructures
                 (filename, sessionsFromCustomer,viewsFromSession,buysFromSession
            /* add parameters as needed */
            );
         printStatistics(sessionsFromCustomer,viewsFromSession,buysFromSession);
         /* add parameters as needed */

      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }

   }
}
