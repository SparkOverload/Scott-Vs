package spark.game01.core.ContactObject;


import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;
import spark.game01.core.Screen.Gameplay00;
import spark.game01.core.Screen.Gameplay01;
import spark.game01.core.character.Scott_char.Scott;
import spark.game01.core.character.Tom_char.Tom;

public class ContactScott {

    public static void contact(Contact contact, Scott scott) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if(scott.body==a && (Gameplay00.bodies.get(b))!= "ground"){
            scott.contacted = true;
            scott.other = contact.getFixtureB().getBody();
        }else if(scott.body==b && (Gameplay00.bodies.get(a))!= "ground"){
            scott.contacted = true;
            scott.other = contact.getFixtureA().getBody();
        }

        if(scott.body==a && (Gameplay01.bodies.get(b))!= "ground"){
            scott.contacted = true;
            scott.other = contact.getFixtureB().getBody();
        }else if(scott.body==b && (Gameplay01.bodies.get(a))!= "ground"){
            scott.contacted = true;
            scott.other = contact.getFixtureA().getBody();
        }

        switch(scott.state){
            case JUMP:
                if((scott.body==a && (Gameplay00.bodies.get(b))== "ground")||
                        (scott.body==b && (Gameplay00.bodies.get(a))== "ground")){
                    scott.state=Scott.State.IDLE;
                }
                if((scott.body==a && (Gameplay01.bodies.get(b))== "ground")||
                        (scott.body==b && (Gameplay01.bodies.get(a))== "ground")){
                    scott.state=Scott.State.IDLE;
                }
                break;
            case LJUMP:
                if((scott.body==a && (Gameplay00.bodies.get(b))== "ground")||
                        (scott.body==b && (Gameplay00.bodies.get(a))== "ground")){
                    scott.state=Scott.State.LIDLE;
                }
                if((scott.body==a && (Gameplay01.bodies.get(b))== "ground")||
                        (scott.body==b && (Gameplay01.bodies.get(a))== "ground")){
                    scott.state=Scott.State.LIDLE;
                }
                break;
            case JKICK:
                if (scott.spriteIndex>=137&&scott.spriteIndex<=143) {
                    if (a == scott.body) {
                        b.applyLinearImpulse(new Vec2(50f, -50f), b.getPosition());
                    } else {
                        a.applyLinearImpulse(new Vec2(50f, -50f), a.getPosition());
                    }
                }
                scott.state=Scott.State.IDLE;
                break;
            case LJKICK:
                if (scott.spriteIndex>=329&&scott.spriteIndex<=335) {
                    if (a == scott.body) {
                        b.applyLinearImpulse(new Vec2(50f, -50f), b.getPosition());
                    } else {
                        a.applyLinearImpulse(new Vec2(50f, -50f), a.getPosition());
                    }
                }
                scott.state=Scott.State.LIDLE;
                break;
            case CHARGE:
                if (scott.spriteIndex>=189&&scott.spriteIndex<=191) {
                    if (a == scott.body) {
                        b.applyLinearImpulse(new Vec2(30f, -50f), b.getPosition());
                    } else {
                        a.applyLinearImpulse(new Vec2(30f, -50f), a.getPosition());
                    }
                }
                break;
            case LCHARGE:
                if (scott.spriteIndex>=380&&scott.spriteIndex<=383) {
                    if (a == scott.body) {
                        b.applyLinearImpulse(new Vec2(-30f, -50f), b.getPosition());
                    }else {
                        a.applyLinearImpulse(new Vec2(-30f, -50f), a.getPosition());
                    }
                }
                break;
            case HEADBUTT:
                if (scott.spriteIndex>=192&&scott.spriteIndex<=201) {
                    if (a == scott.body) {
                        b.applyLinearImpulse(new Vec2(50f, -50f), b.getPosition());
                    } else {
                        a.applyLinearImpulse(new Vec2(50f, -50f), a.getPosition());
                    }
                }
                break;
            case LHEADBUTT:
                if (scott.spriteIndex>=384&&scott.spriteIndex<=393) {
                    if (a == scott.body) {
                        b.applyLinearImpulse(new Vec2(-50f, -50f), b.getPosition());
                    } else {
                        a.applyLinearImpulse(new Vec2(-50f, -50f), a.getPosition());
                    }
                }
                break;
            case ULTIB1:
                if (scott.spriteIndex>=147&&scott.spriteIndex<=152) {
                    if (a == scott.body) {
                        b.applyLinearImpulse(new Vec2(0f, -50f), b.getPosition());
                    } else {
                        a.applyLinearImpulse(new Vec2(0f, -50f), a.getPosition());
                    }
                }
                break;
            case LULTIB1:
                if (scott.spriteIndex>=339&&scott.spriteIndex<=344) {
                    if (a == scott.body) {
                        b.applyLinearImpulse(new Vec2(0f, -50f), b.getPosition());
                    } else {
                        a.applyLinearImpulse(new Vec2(0f, -50f), a.getPosition());
                    }
                }
                break;
        }
    }
}
