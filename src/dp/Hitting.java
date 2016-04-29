/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 *
 * @author Hariom
 */
public class Hitting
{
 private static final String[] MAX_URLS =
    {"http://www.cognatica.com", "http://www.facebook.com/rxnexgen", "http://www.rxnexgen.com", "http://twitter.com/rxnexgen"};
    static String add;
 public static void main(String[] args) throws MalformedURLException, IOException {
 
 
     for(int i=0;i<=MAX_URLS.length;i++)
 {
    add=MAX_URLS[i];
    URL cog_url=new URL(add);
    URLConnection cogcon=cog_url.openConnection();
    long d=cogcon.getDate();
     System.out.println("Date of-"+MAX_URLS[i]+"-is:"+new Date(d));
     d=cogcon.getLastModified();
     System.out.println("the url--"+MAX_URLS[i]+"-is last modified on:"+new Date(d));
 }
     
    }
}
