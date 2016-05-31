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
        if((matt.body==a && scott.body== b)||
                (matt.body==b && scott.body==a)){
            if(scott.spriteIndex>189&&scott.spriteIndex<=192){   //lwasattk2
                matt.body.applyLinearImpulse(new Vec2(30f, -50f), matt.body.getPosition());
                matt.state = Matt.State.LWASATTK2;
                Gameplay01.scorem -=10;
            }
            if(scott.spriteIndex>380&&scott.spriteIndex<=383){   //wasattk2
                matt.body.applyLinearImpulse(new Vec2(-30f, -50f), matt.body.getPosition());
                matt.state = Matt.State.WASATTK2;
                Gameplay01.scorem -=10;
            }
            if(scott.spriteIndex>192&&scott.spriteIndex<=202){     //wasHeadputt
                matt.body.applyLinearImpulse(new Vec2(30f, -50f), matt.body.getPosition());
                matt.state = Matt.State.LWASATTK2;
                Gameplay01.scorem -=10;
            }
            if(scott.spriteIndex>384&&scott.spriteIndex<=394){     //wasHeadputt
                matt.body.applyLinearImpulse(new Vec2(-30f, -50f), matt.body.getPosition());
                matt.state = Matt.State.WASATTK2;
                Gameplay01.scorem -=10;
            }
            if(scott.spriteIndex>137&&scott.spriteIndex<=144){
                matt.body.applyLinearImpulse(new Vec2(30f, -50f), matt.body.getPosition());
                matt.state = Matt.State.LWASATTK2;
                Gameplay01.scorem -=10;
            }
            if(scott.spriteIndex>329&&scott.spriteIndex<=336){
                matt.body.applyLinearImpulse(new Vec2(-30f, -50f), matt.body.getPosition());
                matt.state = Matt.State.WASATTK2;
                Gameplay01.scorem -=10;
            }
            if(scott.spriteIndex>147&&scott.spriteIndex<=153){
                matt.body.applyLinearImpulse(new Vec2(0f, -50f), matt.body.getPosition());
                matt.state = Matt.State.LWASATTK2;
                Gameplay01.scorem -=10;
            }
            if(scott.spriteIndex>339&&scott.spriteIndex<=345){
                matt.body.applyLinearImpulse(new Vec2(0f, -50f), matt.body.getPosition());
                matt.state = Matt.State.WASATTK2;
                Gameplay01.scorem -=10;
            }
            if(scott.spriteIndex>167&&scott.spriteIndex<=171){
                matt.body.applyLinearImpulse(new Vec2(0f, -50f), matt.body.getPosition());
                matt.state = Matt.State.LWASATTK2;
                Gameplay01.scorem -=10;
            }
            if(scott.spriteIndex>359&&scott.spriteIndex<=362){
                matt.body.applyLinearImpulse(new Vec2(0f, -50f), matt.body.getPosition());
                matt.state = Matt.State.WASATTK2;
                Gameplay01.scorem -=10;
            }
            if(scott.spriteIndex>111&&scott.spriteIndex<=123){
                matt.body.applyLinearImpulse(new Vec2(30f, -50f), matt.body.getPosition());
                matt.state = Matt.State.LWASATTK2;
                Gameplay01.scorem -=10;
            }
            if(scott.spriteIndex>286&&scott.spriteIndex<=298){
                matt.body.applyLinearImpulse(new Vec2(-30f, -50f), matt.body.getPosition());
                matt.state = Matt.State.WASATTK2;
                Gameplay01.scorem -=10;
            }
            // ###################  WasAttk   ######################
        }
    }
}
