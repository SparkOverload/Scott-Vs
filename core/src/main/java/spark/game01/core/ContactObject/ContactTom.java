package spark.game01.core.ContactObject;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;
import spark.game01.core.Screen.Gameplay00;
import spark.game01.core.character.Scott_char.Scott;
import spark.game01.core.character.Tom_char.Tom;


public class ContactTom {

    public static void contact(Contact contact, Scott scott, Tom tom) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if(tom.body==a && (Gameplay00.bodies.get(b))!= "ground"){
            tom.contacted = true;
            tom.other = contact.getFixtureB().getBody();
        }else if(tom.body==b && (Gameplay00.bodies.get(a))!= "ground"){
            tom.contacted = true;
            tom.other = contact.getFixtureA().getBody();
        }

        switch (tom.state){
            case LWASATTK2:
                if((tom.body==a && (Gameplay00.bodies.get(b))== "ground")||
                        (tom.body==b && (Gameplay00.bodies.get(a))== "ground")){
                         tom.body.setActive(false);
                         tom.i1=50;
                }
                break;
            case WASATTK2:
                if((tom.body==a && (Gameplay00.bodies.get(b))== "ground")||
                        (tom.body==b && (Gameplay00.bodies.get(a))== "ground")){
                         tom.body.setActive(false);
                         tom.i1=50;
                }
                break;
        }


        // ###################  WasAttk   ######################
        if((tom.body==a && scott.body== b)||
                (tom.body==b && scott.body==a)){
        if(scott.spriteIndex>189&&scott.spriteIndex<=192){   //lwasattk2
                tom.body.applyLinearImpulse(new Vec2(30f, -50f), tom.body.getPosition());
                tom.state = Tom.State.LWASATTK2;
                Gameplay00.scoret -=10;
        }
        if(scott.spriteIndex>380&&scott.spriteIndex<=383){   //wasattk2
                tom.body.applyLinearImpulse(new Vec2(-30f, -50f), tom.body.getPosition());
                tom.state = Tom.State.WASATTK2;
                Gameplay00.scoret -=10;
        }
        if(scott.spriteIndex>192&&scott.spriteIndex<=202){     //wasHeadputt
            tom.body.applyLinearImpulse(new Vec2(30f, -50f), tom.body.getPosition());
            tom.state = Tom.State.LWASATTK2;
            Gameplay00.scoret -=10;
        }
        if(scott.spriteIndex>384&&scott.spriteIndex<=394){     //wasHeadputt
            tom.body.applyLinearImpulse(new Vec2(-30f, -50f), tom.body.getPosition());
            tom.state = Tom.State.WASATTK2;
            Gameplay00.scoret -=10;
        }
        if(scott.spriteIndex>137&&scott.spriteIndex<=144){
            tom.body.applyLinearImpulse(new Vec2(30f, -50f), tom.body.getPosition());
            tom.state = Tom.State.LWASATTK2;
            Gameplay00.scoret -=10;
        }
        if(scott.spriteIndex>329&&scott.spriteIndex<=336){
            tom.body.applyLinearImpulse(new Vec2(-30f, -50f), tom.body.getPosition());
            tom.state = Tom.State.WASATTK2;
            Gameplay00.scoret -=10;
        }
        if(scott.spriteIndex>147&&scott.spriteIndex<=153){
            tom.body.applyLinearImpulse(new Vec2(0f, -50f), tom.body.getPosition());
            tom.state = Tom.State.LWASATTK2;
            Gameplay00.scoret -=10;
        }
        if(scott.spriteIndex>339&&scott.spriteIndex<=345){
            tom.body.applyLinearImpulse(new Vec2(0f, -50f), tom.body.getPosition());
            tom.state = Tom.State.WASATTK2;
            Gameplay00.scoret -=10;
        }
        if(scott.spriteIndex>167&&scott.spriteIndex<=171){
            tom.body.applyLinearImpulse(new Vec2(0f, -50f), tom.body.getPosition());
            tom.state = Tom.State.LWASATTK2;
            Gameplay00.scoret -=10;
        }
        if(scott.spriteIndex>359&&scott.spriteIndex<=362){
            tom.body.applyLinearImpulse(new Vec2(0f, -50f), tom.body.getPosition());
            tom.state = Tom.State.WASATTK2;
            Gameplay00.scoret -=10;
        }
        if(scott.spriteIndex>111&&scott.spriteIndex<=123){
            tom.body.applyLinearImpulse(new Vec2(30f, -50f), tom.body.getPosition());
            tom.state = Tom.State.LWASATTK2;
            Gameplay00.scoret -=10;
        }
        if(scott.spriteIndex>286&&scott.spriteIndex<=298){
            tom.body.applyLinearImpulse(new Vec2(-30f, -50f), tom.body.getPosition());
            tom.state = Tom.State.WASATTK2;
            Gameplay00.scoret -=10;
        }
        // ###################  WasAttk   ######################
    }
    }
}
