package com.sparkleusc.sparklesparkhere;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import messages.*;

class Client extends AsyncTask<Message, Void, Message> {

    private static final String IP = "10.0.2.2";
    private static final int port = 6789;
    private TaskDelegate delegate;

    Client(TaskDelegate delegate){
        this.delegate = delegate;
    }

    protected Message doInBackground(Message... m){
        Message message1 = m[0];
        Message message = null;
        Socket s = null;
        try{
            Log.d("in doInBack re socket", "sajd");
            s = new Socket(IP, port);
            Log.d("connected to server", "asjkdhf");
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.flush();
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            oos.writeObject(message1);
            oos.flush();
            message = (Message) ois.readObject();
            Log.d("connected to server", "received message; action: "+message.getClass());
            System.out.println("received message; action: "+message.getClass());
            if (message instanceof UserMessage){
                UserMessage mess = (UserMessage) message;
                System.out.println("properly reading User message");
                if (mess.action.equals(Message.insert)){
                    System.out.println(mess.action);
                   // Log.d("user current role ", mess.user.getCurrent_role().getProfile().getPhoneNumber());
                    //UserMessage mess = (UserMessage) message;
                    System.out.println(mess.user.getUser_id());
                }
                if (mess.action.equals(Message.get)){
                    System.out.println(mess.action);
                   // Log.d("user current role ", mess.user.getCurrent_role().getProfile().getPhoneNumber());
                    //UserMessage mess = (UserMessage) message;
                    System.out.println(mess.user.getUser_id());
                }
                else if (mess.action.equals(Message.check_validity)){
                    System.out.println("is valid: "+mess.success);
                }

            }
            else if (message instanceof LenderMessage){
                LenderMessage lendMess = (LenderMessage) message;
                System.out.println("properly reading Lender message");
                if (lendMess.action.equals(Message.insert)){
                    System.out.println("lender insert "+ lendMess.lender.getLenderId());
                }
            }
            else if (message instanceof SeekerMessage){
                SeekerMessage seekMess = (SeekerMessage) message;
                System.out.println("properly reading Seeker message");
                if (seekMess.action.equals(Message.insert)){
                    System.out.println("seeker insert "+ seekMess.seeker.getSeekerId());
                }
            }
            else if (message instanceof ListingMessage){
                ListingMessage listMess = (ListingMessage) message;
                System.out.println("properly reading Listing message");
                if (listMess.action.equals(Message.insert)){
                    System.out.println("listing insert "+ listMess.listing.getListingId());
                }
            }
            else if (message instanceof ReservationMessage) {
                ReservationMessage resMess = (ReservationMessage) message;
                System.out.println("properly reading Reservation message");
                if (resMess.action.equals(Message.insert)) {
                    System.out.println("reservation insert " + resMess.reservation.getReservationId());
                }
            }
        } catch (IOException | ClassNotFoundException e){
            System.out.println("exception in client send Message: "+e.getMessage());
        }
        finally{
            if (s!= null) {
                try{s.close();}
                catch (IOException e){System.out.println("exception in client send Message: "+e.getMessage());}
            }
        }
        return message;
    }

    protected void onPostExecute(Message response) {
         delegate.taskCompletionResult(response);
    }
}
