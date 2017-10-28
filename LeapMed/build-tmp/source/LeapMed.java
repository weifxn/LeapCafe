import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import de.voidplus.leapmotion.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class LeapMed extends PApplet {

/*
changelog:
1.0 more than one selection doesnt work
1.1 previous issue fixed, quantity and size in progress(confirm & cancel completed).
1.2 size & quantity completed, work on object(cart) *left pricetag
    added pricetag, added object Cart to retrieve info *left order.display()
1.3 order.display completed.  Need to improve the transition though.
    transition completed.
    need to reposition the quantity and size to the sides. done
    switch cart page with front page for logo. done
    cart move to other page. done
2.0 Functional with all description included. Need to work on cart page.
    Return from cart works now, where to put confirm cart button?
    add background for floating text
    move cart confirm and return to the sides < return confirm > (transition)
    add the cart logo. done(add table title)
    slow down increase size and quantity. done*2
    subtotal! done
    add thank you page done (timer problem tho)
    lower the confirm and return height in page 3


*/


LeapMotion leap;
PImage bg, bg1, bg2, coffee;
PImage a1,a2,a3,a4,a5;
PImage ico, cursor;
PImage ty,cartpage,cart,logo,current, qs1,qs2,qs3,qs4,qs5,qs6,up,down;
PFont paper;
int currentScreen = 0;
int realCount, realCountQ, realCountS;
int count = 5, countQ = 1, countS = 1;
int countdecrease, countQdecrease, countSdecrease, countQincrease, countSincrease;
int countIncrease = 1;
int alpha = 543, delta = 7;
int alpha1 = 0, alpha2 = -618, alpha3 = 757, alphaText = 963,alpha5 = 766,alpha6 = -800;
int position; //0.start 1.back 2.?? 3 ~ 8 are drinks
int iscreen;
String drinkName;
int fromPage;
int returnPage;
boolean confirm = false;
int cupSize;
ArrayList<Cart> order;
int id = 1;
int price;
String sizeName;
int drinkPrice;
int tempPosition;
int sumTotal=0;

Rain r1;
int numDrops = 40;
Rain[] drops = new Rain[numDrops];


public void setup(){
  //fullScreen();
  
  bg = loadImage("covfefe1.png");
  bg1 = loadImage("covfefe3cpy.png");
  bg2 = loadImage("covfefe4.png");
  a1 = loadImage("black.png");
  a2 = loadImage("cappucino1.png");
  a3 = loadImage("espresso1.png");
  a4 = loadImage("mocha1.png");
  a5 = loadImage("latte1.png");
  qs1 = loadImage("c1.png");
  qs2 = loadImage("c2.png");
  qs3 = loadImage("c3.png");
  qs4 = loadImage("c4.png");
  qs5 = loadImage("c5.png");
  up = loadImage("up.png");
  down = loadImage("down.png");
  cursor = loadImage("cursor.png");
  logo = loadImage("logo.png");
  cart = loadImage("2772.png");
  cartpage = loadImage("cart with confirm.png");
  ty = loadImage("Thank You Page.png");
  leap = new LeapMotion(this).allowGestures();
  textSize(50);
  
  paper = createFont("paperdaisy.ttf", 50);
  textFont(paper);
  order = new ArrayList<Cart>();
  for (int i = 0; i < drops.length; i++) {
    drops[i] = new Rain();
    r1 = new Rain();
  }
}

public void draw(){
  switch(currentScreen) {
    case 0: draw0(); break;
    case 1: draw1(); break;
    case 2: draw2(); break;
    case 3: draw3(); break;
    case 4: draw4(); break;
    default: background(0);break;
  }
}

public void draw0(){ //Move finger to middle to begin.
  alpha = 543;
  alpha2 =0;
  image(bg,0,0);
  image(bg1,0,alpha);
  image(logo,0,alpha2);
  currentScreen = iscreen;
  fill(255);
  text("Pinch here to begin", width/2 - 10, height*3/4);
  timer(countdecrease);

  for (Hand hand : leap.getHands ()) {
    for (Finger finger : hand.getFingers()) {
      switch(finger.getType()){
        case 1:
        image(cursor,hand.getStabilizedPosition().x-60,hand.getStabilizedPosition().y-60);
        if(hand.getStabilizedPosition().y > height*3/4 - 90){
            position = 0;

              countdecrease = 1;

      }  else {countdecrease = 0;
          realCount = 5;
        }
        if(realCount == 0){
          iscreen = getPosition(position);
          currentScreen = iscreen;
          realCount = 5;
          countdecrease = 0;
          fromPage = 0;
        }
      }
     }
   }
}

public void draw1(){
  fill(0);
  image(bg,0,alpha1);
  image(bg1,0,alpha);
  image(logo,0,alpha2);
  if(alpha5 > height *5/7){ alpha5 -=delta;
  }
  image(cart,width*7/8,alpha5,80,80);
  textAlign(CENTER);
  if(fromPage == 0){
    currentScreen = iscreen;
    if(alpha2 < -420){
      if (alpha > 0 ) { alpha-=delta; }
    }
    alpha1 = 0;
    if(alpha2 > -800){alpha2-=delta;}
  }
  if(fromPage == 3){
    if(alpha2 < -420){
      if (alpha > 0 ) { alpha-=delta; }
    }
    alpha1 = 0;
    if(alpha2 > -600){alpha2-=delta;}
  }
  if(fromPage == 2){
    if(alpha2 < -600){alpha2 += delta;}
    if(alpha < 0) {alpha += delta;}
    if(alpha1 < 1){alpha1 += delta;}
    if(alphaText < 963){alphaText += delta*4;}
    if(alpha3 < 721){alpha3 += delta*4;
      current = getDesc(tempPosition);
      image(current,(width/2)-270-25,alpha3-35);}
  }
  if(fromPage == 99){ //confirm
    if(alpha2 < -600){alpha2 += delta;}
    if(alpha < 0) {alpha += delta;}
    if(alpha1 < 1){alpha1 += delta;}
    if(alphaText < 963){alphaText += delta;}
    if(alpha3 < 721){alpha3 += delta;
      current = getDesc(tempPosition);
      image(current,(width/2)-270-25,alpha3-35);}
  }

  rectMode(CENTER);
  timer(countdecrease);
  image(up,width/2 + 305 +15, alphaText-140,60,60);
  image(up,width/2 - 395 + 15, alphaText-140,60,60);
  image(down,width/2 + 305 +15, alphaText+100,60,60);
  image(down,width/2 - 395 +15, alphaText+100,60,60);

  text("Size:\n" + sizeName ,width/2 +350,alphaText);
  text("Quantity:\n"+realCountQ, width/2 - 350, alphaText);
  for (Hand hand : leap.getHands ()) {
    for (Finger finger : hand.getFingers()) {
     switch(finger.getType()){
       case 1:
        drinkName = getDrink(position);
        drinkPrice = getPrice(position);
        if(position == 0){
          countdecrease = 0;
          realCount = 5;
        }

        if(realCount == 0){
          iscreen = getPosition(position);
          currentScreen = iscreen;
          fromPage = 1;
          realCount = 5;
          countdecrease = 0;

        }
        //noFill();
        //ellipse(hand.getStabilizedPosition().x,hand.getStabilizedPosition().y,70,70);
        image(cursor,hand.getStabilizedPosition().x - 60,hand.getStabilizedPosition().y-60);
        if(hand.getStabilizedPosition().x > 393 &&
          hand.getStabilizedPosition().x < 454 &&
          hand.getStabilizedPosition().y < 393 &&
          hand.getStabilizedPosition().y > 250){
            countdecrease = 1;
            position = 3;
            fill(0,0,0, 180);
            rect(hand.getStabilizedPosition().x-40,hand.getStabilizedPosition().y+160,125,240,20);
            fill(255);
            text(drinkName + "\nRM" + drinkPrice + "\n" + realCount,hand.getStabilizedPosition().x-38,hand.getStabilizedPosition().y+90);        }
        else {
          position = 0;
        }
        if(hand.getStabilizedPosition().x > 493 &&
          hand.getStabilizedPosition().x < 572 &&
          hand.getStabilizedPosition().y < 393 &&
          hand.getStabilizedPosition().y > 250){
            countdecrease = 1;
            position = 4;
            noStroke();
            fill(0,0,0,180);
            rect(hand.getStabilizedPosition().x-40,hand.getStabilizedPosition().y+160,125,240,20);
            fill(255);
            text(drinkName + "\nRM" + drinkPrice + "\n" + realCount,hand.getStabilizedPosition().x-38,hand.getStabilizedPosition().y+90);
        }
        if(hand.getStabilizedPosition().x > 593 &&
          hand.getStabilizedPosition().x < 672 &&
          hand.getStabilizedPosition().y < 393 &&
          hand.getStabilizedPosition().y > 250){
            countdecrease = 1;
            position = 5;
            fill(0,0,0,180);
            rect(hand.getStabilizedPosition().x-40,hand.getStabilizedPosition().y+160,125,240,20);
            fill(255);
            text(drinkName + "\nRM" + drinkPrice + "\n" + realCount,hand.getStabilizedPosition().x-38,hand.getStabilizedPosition().y+90);
            }
        if(hand.getStabilizedPosition().x > 693 &&
          hand.getStabilizedPosition().x < 772 &&
          hand.getStabilizedPosition().y < 393 &&
          hand.getStabilizedPosition().y > 250){
            countdecrease = 1;
            position = 6;
            fill(0,0,0,180);
            rect(hand.getStabilizedPosition().x-40,hand.getStabilizedPosition().y+160,125,240,20);
            fill(255);
            text(drinkName + "\nRM" + drinkPrice + "\n" + realCount,hand.getStabilizedPosition().x-38,hand.getStabilizedPosition().y+90);
            }
        if(hand.getStabilizedPosition().x > 793 &&
          hand.getStabilizedPosition().x < 872 &&
          hand.getStabilizedPosition().y < 393 &&
          hand.getStabilizedPosition().y > 250){
            countdecrease = 1;
            position = 7;
            fill(0,0,0,180);
            rect(hand.getStabilizedPosition().x-40,hand.getStabilizedPosition().y+160,125,240,20);
            fill(255);
            text(drinkName + "\nRM" + drinkPrice + "\n" + realCount,hand.getStabilizedPosition().x-38,hand.getStabilizedPosition().y+90);    }
        if(hand.getStabilizedPosition().x > 993){
            position = 8;
            text("SHOW CART\n",hand.getStabilizedPosition().x-18,hand.getStabilizedPosition().y+75);
            if(mousePressed == true){
            realCount = 1;
            countdecrease = 1;
          }
        }
      }
    }
  }
}

public void draw2(){
  if (alpha > 0 ) {alpha-=delta; }
  if (alpha1 > -124) {alpha1 -= delta;}
  if (alpha3 > 214) {alpha3 -= delta;}
  if (alpha2 > -650) {alpha2 -= delta;}
  image(bg,0,alpha1);
  image(bg1,0,alpha);
  image(bg2,0,alpha2);
  if(alpha5 < 766){ alpha5 +=delta;
  }
  image(cart,width*7/8,alpha5,80,80);
  current = getDesc(position);
  image(current,(width/2)-270-25,alpha3-35);
  rectMode(CENTER);
  timer(countdecrease);
  cupSize = realCountS;
  sizeName = cupSizeNames(cupSize);
  fill(0);
  textAlign(CENTER);
  text("Size:\n" + sizeName ,width/2 +350,alphaText);
  text("Quantity:\n"+realCountQ, width/2 - 350, alphaText);
  image(up,width/2 + 305 +15, alphaText-140,60,60);
  image(up,width/2 - 395 + 15, alphaText-140,60,60);
  image(down,width/2 + 305 +15, alphaText+120,60,60);
  image(down,width/2 - 395 +15, alphaText+120,60,60);
  if(alphaText > 420){
    alphaText-=delta;
  }
  for (Hand hand : leap.getHands ()) {
    for (Finger finger : hand.getFingers()) {
      switch(finger.getType()){
        case 1:

        //quantity
        if(countQincrease == 1){
          countQ +=countIncrease;
          }
        if(countQdecrease == 1){
          countQ -= countIncrease;
        }
        if(countQ == 35){
          realCountQ += 1;
          countQ =0;
        }
        if (countQ == -35){
          realCountQ -= 1;
          countQ = 0;
        }
        if (realCountQ <1){
          realCountQ = 1;
          countQ = 0;
        }

        //size
        if(countSincrease == 1){
          countS +=countIncrease;
          }
        if(countSdecrease == 1){
          countS -= countIncrease;
        }
        if(countS == 35){
          realCountS += 1;
          countS =0;
        }
        if (countS == -35){
          realCountS -= 1;
          countS = 0;
        }
        if (realCountS <0){
          realCountS = 0;
          countS = 0;
        }
        if(realCountS > 3){
          realCountS = 3;
          countS = 0;
        }
        if(returnPage == 0){ //cancel
          countdecrease = 0;
          realCount = 5;
        }
        if(realCount == 0){ //confirm
          if(confirm = true){
            price = getPrice(position);
            price *= realCountQ;
            price += realCountS*1;
            order.add(new Cart(id, drinkName, sizeName, realCountQ, price));
            sumTotal(price);
            id += 1;
            realCountS = 1;
            realCountQ = 1;
            tempPosition = position;
          }
          iscreen = back(returnPage);
          currentScreen = iscreen;
          realCount = 5;
          countdecrease = 0;
          realCountS = 1;
          tempPosition = position;

          fromPage = 2;
        }

        ico = getImage(position);
        image(ico,hand.getStabilizedPosition().x,hand.getStabilizedPosition().y);

        if(hand.getStabilizedPosition().y < 230 && hand.getStabilizedPosition().x > 453 && hand.getStabilizedPosition().x < 700){
            countdecrease = 1;
            returnPage = 1;
            text("Return\n" + realCount,hand.getStabilizedPosition().x-8,hand.getStabilizedPosition().y+18);
            fromPage = 2;
        }
        else {
          returnPage = 0;
          confirm = false;
        }
        if(hand.getStabilizedPosition().y > 500 && hand.getStabilizedPosition().x > 453 && hand.getStabilizedPosition().x < 700){
            returnPage = 1;
            fromPage = 99;
            confirm = true;
            if(alpha3 > 150){alpha3-=delta;}
            //mousePressed == true
            //mousePressed == true
            if(mousePressed == true){
              realCount = 1;
              countdecrease = 1;
              }
        } else{
          if (alpha3 < 214) {alpha3 += delta;}
        }


        //quantity
        if(hand.getStabilizedPosition().x < 353 &&
          hand.getStabilizedPosition().y > 500){
            countQdecrease = 1;
            text("Decrease quantity " + realCountQ,hand.getStabilizedPosition().x-8,hand.getStabilizedPosition().y+18);
        } else {
          countQdecrease = 0;
        }
        if(hand.getStabilizedPosition().x < 353 &&
          hand.getStabilizedPosition().y < 320){
            countQincrease = 1;
            text("Increase quantity\n" + realCountQ,hand.getStabilizedPosition().x-8,hand.getStabilizedPosition().y+18);
        } else {
          countQincrease = 0;
        }

        //size
        if(hand.getStabilizedPosition().x > 862 &&
          hand.getStabilizedPosition().y > 520){
            countSdecrease = 1;
            text("Decrease size\n" + sizeName,hand.getStabilizedPosition().x-8,hand.getStabilizedPosition().y+18);
        } else {
          countSdecrease = 0;
        }
        if(hand.getStabilizedPosition().x > 862 &&
          hand.getStabilizedPosition().y < 320){
            countSincrease = 1;
            text("Increase size " + sizeName,hand.getStabilizedPosition().x-8,hand.getStabilizedPosition().y+18);
        } else {
          countSincrease = 0;
        }


      }
    }
  }
}

public void draw3(){
  fromPage = 3;
  confirm = false;
  int alphaTitle = -99;
  if (alpha2 < -80  ) {alpha2+=delta; }
  if (alpha <= 543){alpha+=delta;}
  image(bg,0,0);
  image(bg1,0,alpha);
  image(cartpage,0,alpha2);
  if(alpha5 < 766){ alpha5 +=delta;
  }
  image(cart,width*7/8,alpha5,80,80);
  fill(255);
  if(alphaTitle < height/4-50){alphaTitle += delta;}
  text("Name", width/5, alphaTitle);
  text("Size", width*2/5, alphaTitle);
  text("Quantity", width*3/5, alphaTitle);
  text("Price", width*4/5, alphaTitle);
  for(int i = 0; i < order.size(); i++){
    Cart orders = order.get(i);
    orders.display();
  }
  timer(countdecrease);
  text("     subtotal:", width*4/7,height*5/7);

  text("RM"+sumTotal,width*5/7,height*5/7);
  for (Hand hand : leap.getHands ()) {
    for (Finger finger : hand.getFingers()) {
      switch(finger.getType()){
        case 1:
      if(realCount == 0){ //confirm
        if(confirm = true){
          order.clear();
          currentScreen = iscreen;
          id = 1;
          fromPage = 3;
          realCount = 5;
        }
        iscreen = back(returnPage);
        currentScreen = iscreen;
        fromPage = 3;
        realCount = 5;
        countdecrease = 0;
        tempPosition = position;
      }
        image(cursor,hand.getStabilizedPosition().x - 60,hand.getStabilizedPosition().y-60);
        /*if(hand.getStabilizedPosition().y > 560 && hand.getStabilizedPosition().x > 426){
            countdecrease = 1;
            returnPage = 1;
            text("Return\n" + realCount,hand.getStabilizedPosition().x-8,hand.getStabilizedPosition().y+68);
            fromPage = 3;
          }*/


        if(hand.getStabilizedPosition().x < 426 ){
            countdecrease = 1;
            returnPage = 4;
            text("Pinch here\nto confirm",hand.getStabilizedPosition().x-8,hand.getStabilizedPosition().y+68);
            fromPage = 3;
            confirm = true;
            if(mousePressed == true){
              realCount = 1;
              countdecrease = 1;
              }

        }else {
          countdecrease = 0;
          realCount = 5;
        }


      }
  }
}
}

public void draw4(){
  image(bg,0,0);
  image(cartpage,0,alpha2);
  if(alpha2 > -800){alpha2 -=delta*3;}
  fromPage = 4;
  if(alpha6 < -80) {alpha6 += delta*3;}
  image(ty,0,alpha6);
  timer(countdecrease);
  returnPage = 0;
  /*for (int i = 0; i < drops.length; i++) {
    drops[i].fall();
  }*/

  for (Hand hand : leap.getHands ()) {
    for (Finger finger : hand.getFingers()) {
       switch(finger.getType()){
        case 1:
         if(realCount == 0){ //confirm
           iscreen = back(returnPage);
           currentScreen = iscreen;
           fromPage = 3;
           countdecrease = 0;
         }
         if(hand.getStabilizedPosition().y > 0){
             countdecrease = 1;
             returnPage = 1;
             fromPage = 2;
         }
         else {
           returnPage = 0;
           countdecrease = 0;
         }
       }
     }
   }

}

public int timer(int countdecrease){
  if(countdecrease == 1){
    count -= countIncrease;
  }
  if(count == 25){
    realCount += 1;
    count =0;
  }
  if (count == -25){
    realCount -= 1;
    count = 0;
  }
  if (realCount <0){
    realCount = 0;
    count = 0;
  }
  return realCount;
}

public int back(int returnPage){
  return returnPage;
}

public int getPosition(int position){
  if(position == 0)
  {
    return 1;
  }
  if(position == 1)
  {
    return 1;
  }
  if(position > 2 && position < 8)
  {
    return 2;
  }
  if(position == 8)
  {
    return 3;
  }
  else return position;
}

public String getDrink(int position){
  if(position == 3){return "Black";}
    if(position == 4){return "Cappuccino";}
      if(position == 5){return "Espresso";}
        if(position == 6){return "Mocha";}
          if(position == 7){return "Latte";}
            else return "";
}
public int getPrice(int position){
  if(position == 3){return 4;}
    if(position == 4){return 7;}
      if(position == 5){return 4;}
        if(position == 6){return 6;}
          if(position == 7){return 6;}
            else return 0;
}

public PImage getImage(int position){
  if(position == 3){return a1;}
    if(position == 4){return a2;}
      if(position == 5){return a3;}
        if(position == 6){return a4;}
          if(position == 7){return a5;}
            else return a1;
}

public PImage getDesc(int position){
  if(position == 3){return qs1;}
    if(position == 4){return qs2;}
      if(position == 5){return qs3;}
        if(position == 6){return qs4;}
          if(position == 7){return qs5;}
            else return qs1;
}
public String cupSizeNames(int cupSize){
  if(cupSize == 0){return "Small";}
  if(cupSize == 1){return "Medium";}
  if(cupSize == 2){return "Large";}
  if(cupSize == 3){return "Extra large";}

  else return "";
}

class Cart{
  int id;
  String name;
  String size;
  int quantity;
  int totalPrice;
  int alphaCart = -289;

  Cart(int id, String name, String size, int quantity, int totalPrice){
    this.id = id;
    this.name = name;
    this.size = size;
    this.quantity = quantity;
    this.totalPrice = totalPrice;
  }

  public void display(){
    if(alphaCart < (height/8)+(50*id)){alphaCart += delta;}
    text(name,width*2/7, alphaCart);
    text(size, width*3/7, alphaCart);
    text(quantity, width*4/7, alphaCart);
    text("RM" + totalPrice, width*5/7, alphaCart);
  }
}

public int sumTotal(int price){
   sumTotal += price;
   return price;
}

class Rain {
  float r = random(1280);
  float y = random(-height);
  PImage pic;
  int ran = PApplet.parseInt(random(3,8));

  public void fall() {
    y = y + 7;
    position = ran;
    pic = getImage(position);
    image(pic, r, y);


   if(y>height){
   r = random(1280);
   y = random(-200);
   }

  }
}

public void leapOnSwipeGesture(SwipeGesture g, int state){
  int     id               = g.getId();
  Finger  finger           = g.getFinger();
  PVector position         = g.getPosition();
  PVector positionStart    = g.getStartPosition();
  PVector direction        = g.getDirection();
  float   speed            = g.getSpeed();
  long    duration         = g.getDuration();
  float   durationSeconds  = g.getDurationInSeconds();

  switch(state){
    case 1: // Start

      break;
    case 2: // Update
      break;
    case 3: // Stop
    if(id > 9){
    if(fromPage != 3 && fromPage != 1){
    realCount = 5;
    countdecrease = 0;
    realCountS = 1;
    realCountQ = 1;
    currentScreen = 1;

    fromPage = 2;
      println("SwipeGesture: " + id);
    }
    }


  }
}

  public void settings() {  size(1280,720);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "LeapMed" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
