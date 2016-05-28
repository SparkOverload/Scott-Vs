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
            case LSS:
                if (tom.spriteIndex>=98&&tom.spriteIndex<=101) {
                    if (a == tom.body) {
                        b.applyLinearImpulse(new Vec2(-10f, -10f), b.getPosition());
                    } else {
                        a.applyLinearImpulse(new Vec2(-10f, -10f), a.getPosition());
                    }
                }
                break;
            case SS:
                if (tom.spriteIndex>=78&&tom.spriteIndex<=81) {
                    if (a == tom.body) {
                        b.applyLinearImpulse(new Vec2(10f, -10f), b.getPosition());
                    } else {
                        a.applyLinearImpulse(new Vec2(10f, -10f), a.getPosition());
                    }
                }
                break;
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

        if(scott.spriteIndex>=189&&scott.spriteIndex<=191){   //lwasattk2
            if(tom.state == Tom.State.IDLE){
                tom.state = Tom.State.WASATTK2;
                Gameplay00.scoret -=10;
            }else if(tom.state == Tom.State.LIDLE){
                tom.state = Tom.State.LWASATTK2;
                Gameplay00.scoret -=10;
            }else{
                tom.state = Tom.State.LWASATTK2;
                Gameplay00.scoret -=10;
            }

        }
        if(scott.spriteIndex>=380&&scott.spriteIndex<=382){   //wasattk2
            if(tom.state == Tom.State.LIDLE){
                tom.state = Tom.State.LWASATTK2;
                Gameplay00.scoret -=10;
            }else if(tom.state == Tom.State.IDLE){
                tom.state = Tom.State.WASATTK2;
                Gameplay00.scoret -=10;
            }else{
                tom.state = Tom.State.WASATTK2;
                Gameplay00.scoret -=10;
            }
        }
        if(scott.spriteIndex>=41&&scott.spriteIndex<=57){      //wasattk
            tom.state = Tom.State.LWASATTK;
        }
        if(scott.spriteIndex>=298&&scott.spriteIndex<=314){     //wasattk
            tom.state = Tom.State.WASATTK;
        }
        if(scott.spriteIndex>=192&&scott.spriteIndex<=201){     //wasHeadputt
            tom.state = Tom.State.LWASATTK2;
            Gameplay00.scoret -=10;
        }
        if(scott.spriteIndex>=384&&scott.spriteIndex<=393){     //wasHeadputt
            tom.state = Tom.State.WASATTK2;
            Gameplay00.scoret -=10;
        }
        if(scott.spriteIndex>=123&&scott.spriteIndex<=136){     //waskick
            tom.state = Tom.State.LWASATTK;
        }
        if(scott.spriteIndex>=315&&scott.spriteIndex<=328){     //waskick1
            tom.state = Tom.State.WASATTK;
        }
        if(scott.spriteIndex>=137&&scott.spriteIndex<=143){
            tom.state = Tom.State.LWASATTK2;
            Gameplay00.scoret -=10;
        }
        if(scott.spriteIndex>=329&&scott.spriteIndex<=335){
            tom.state = Tom.State.WASATTK2;
            Gameplay00.scoret -=10;
        }
        if(scott.spriteIndex>=147&&scott.spriteIndex<=152){
            tom.state = Tom.State.LWASATTK2;
            Gameplay00.scoret -=10;
        }
        if(scott.spriteIndex>=339&&scott.spriteIndex<=344){
            tom.state = Tom.State.WASATTK2;
            Gameplay00.scoret -=10;
        }
        if(scott.spriteIndex>=167&&scott.spriteIndex<=170){
            tom.state = Tom.State.LWASATTK2;
            Gameplay00.scoret -=10;
        }
        if(scott.spriteIndex>=359&&scott.spriteIndex<=361){
            tom.state = Tom.State.WASATTK2;
            Gameplay00.scoret -=10;
        }
        if(scott.spriteIndex>=111&&scott.spriteIndex<=122){
            tom.state = Tom.State.LWASATTK2;
            Gameplay00.scoret -=10;
        }
        if(scott.spriteIndex>=286&&scott.spriteIndex<=297){
            tom.state = Tom.State.WASATTK2;
            Gameplay00.scoret -=10;
        }
        // ###################  WasAttk   ######################

    }
}
