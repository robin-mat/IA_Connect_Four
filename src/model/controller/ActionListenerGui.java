
package controller;

import java.util.Scanner;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import play.*;

public class ActionListenerGui implements KeyListener {

  public ActionListenerGui(){
  }

  @Override
  public void keyPressed(KeyEvent e) {
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // TODO Auto-generated method stub
  }

  @Override
  public void keyReleased(KeyEvent e){
    int t = e.getKeyCode();

    if (t==65){
      System.out.println("Lol");
    }
  }
}