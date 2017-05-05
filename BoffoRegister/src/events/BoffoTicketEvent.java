/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package events;

/**
 *Last updated: 4-26-17
 *
 *This class extends the generic BoffoEvent and is used for anyone who fires
 * events that pass the ticket
 *
 * @author Maclean Frazier
 */
public class BoffoTicketEvent extends BoffoEvent{
      public BoffoTicketEvent(Object _source, BoffoEventData _messageString){
          super(_source, _messageString);
}
}

