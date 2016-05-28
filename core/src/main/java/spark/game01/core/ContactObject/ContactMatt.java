package spark.game01.core.ContactObject;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;
import spark.game01.core.Screen.Gameplay01;
import spark.game01.core.character.Matt_char.Matt;
import spark.game01.core.character.Scott_char.Scott;


/**
 * Created by SPARK.O on 5/22/2016.
 */
public class ContactMatt {

    public static void contact(Contact contact, Scott scott, Matt matt) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if(matt.body==a && (Gameplay01.bodies.get(b))!= "ground"){
            matt.contacted = true;
            matt.other = contact.getFixtureB().getBody();
        }else if(matt.body==b && (Gameplay01.bodies.get(a))!= "ground"){
            matt.contacted = true;
            matt.other = contact.getFixtureA().getBody();
        }

        switch (matt.state){
            case LWASATTK2:
                if((matt.body==a && (Gameplay01.bodies.get(b))== "ground")||
                        (matt.body==b && (Gameplay01.bodies.get(a))== "ground")){
                    matt.body.setActive(false);
                    matt.i1=50;
                }
                break;
            case WASATTK2:
                if((matt.body==a && (Gameplay01.bodies.get(b))== "ground")||
                        (matt.body==b && (Gameplay01.bodies.get(a))== "ground")){
                    matt.body.setActive(false);
                    matt.i1=50;
                }
                break;
        }



        // ###################  WasAttk   ######################

        if(scott.spriteIndex>=189&&scott.spriteIndex<=191){   //lwasattk2
            if(matt.state == Matt.State.IDLE){
                matt.state = Matt.State.WASATTK2;
                Gameplay01.scorem -=10;
            }else if(matt.state == Matt.State.LIDLE){
                matt.state = Matt.State.LWASATTK2;
                Gameplay01.scorem -=10;
            }else{
                matt.state = Matt.State.LWASATTK2;
                Gameplay01.scorem -=10;
            }

        }
        if(scott.spriteIndex>=380&&scott.spriteIndex<=382){   //wasattk2
            if(matt.state == Matt.State.LIDLE){
                matt.state = Matt.State.LWASATTK2;
                Gameplay01.scorem -=10;
            }else if(matt.state == Matt.State.IDLE){
                matt.state = Matt.State.WASATTK2;
                Gameplay01.scorem -=10;
            }else{
                matt.state = Matt.State.WASATTK2;
                Gameplay01.scorem -=10;
            }
        }
        if(scott.spriteIndex>=41&&scott.spriteIndex<=57){      //wasattk
            matt.state = Matt.State.LWASATTK1;
        }
        if(scott.spriteIndex>=298&&scott.spriteIndex<=314){     //wasattk
            matt.state = Matt.State.WASATTK1;
        }
        if(scott.spriteIndex>=192&&scott.spriteIndex<=201){     //wasHeadputt
            matt.state = Matt.State.LWASATTK2;
            Gameplay01.scorem -=10;
        }
        if(scott.spriteIndex>=384&&scott.spriteIndex<=393){     //wasHeadputt
            matt.state = Matt.State.WASATTK2;
        }
        if(scott.spriteIndex>=123&&scott.spriteIndex<=136){     //waskick
            matt.state = Matt.State.LWASATTK1;
        }
        if(scott.spriteIndex>=315&&scott.spriteIndex<=328){     //waskick1
            matt.state = Matt.State.WASATTK1;
        }
        if(scott.spriteIndex>=137&&scott.spriteIndex<=143){
            matt.state = Matt.State.LWASATTK2;
            Gameplay01.scorem -=10;
        }
        if(scott.spriteIndex>=329&&scott.spriteIndex<=335){
            matt.state = Matt.State.WASATTK2;
            Gameplay01.scorem -=10;
        }
        if(scott.spriteIndex>=147&&scott.spriteIndex<=152){
            matt.state = Matt.State.LWASATTK2;
            Gameplay01.scorem -=10;
        }
        if(scott.spriteIndex>=339&&scott.spriteIndex<=344){
            matt.state = Matt.State.WASATTK2;
            Gameplay01.scorem -=10;
        }
        if(scott.spriteIndex>=167&&scott.spriteIndex<=170){
            matt.state = Matt.State.LWASATTK2;
            Gameplay01.scorem -=10;
        }
        if(scott.spriteIndex>=359&&scott.spriteIndex<=361){
            matt.state = Matt.State.WASATTK2;
            Gameplay01.scorem -=10;
        }
        if(scott.spriteIndex>=111&&scott.spriteIndex<=122){
            matt.state = Matt.State.LWASATTK2;
            Gameplay01.scorem -=10;
        }
        if(scott.spriteIndex>=286&&scott.spriteIndex<=297){
            matt.state = Matt.State.WASATTK2;
            Gameplay01.scorem -=10;
        }
        // ###################  WasAttk   ######################

    }
}
