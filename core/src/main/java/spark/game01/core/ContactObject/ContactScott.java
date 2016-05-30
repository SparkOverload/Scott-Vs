package spark.game01.core.ContactObject;


import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;
import spark.game01.core.Screen.Gameplay00;
import spark.game01.core.Screen.Gameplay01;
import spark.game01.core.Screen.Gameplay02;
import spark.game01.core.character.Scott_char.Scott;
import spark.game01.core.character.Tom_char.Tom;

public class ContactScott {

    public static void contact(Contact contact, Scott scott) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if (scott.body == a && (Gameplay00.bodies.get(b)) != "ground") {
            scott.contacted = true;
            scott.other = contact.getFixtureB().getBody();
        } else if (scott.body == b && (Gameplay00.bodies.get(a)) != "ground") {
            scott.contacted = true;
            scott.other = contact.getFixtureA().getBody();
        }


            switch (scott.state) {
                case JUMP:
                    scott.state = Scott.State.IDLE;
                    break;
                case LJUMP:
                    scott.state = Scott.State.LIDLE;
                    break;
                case JKICK:
                    scott.body.applyLinearImpulse(new Vec2(-70f, -100f), scott.body.getPosition());
                    scott.state = Scott.State.IDLE;
                    break;
                case LJKICK:
                    scott.body.applyLinearImpulse(new Vec2(70f, -100f), scott.body.getPosition());
                    scott.state = Scott.State.LIDLE;
                    break;
                case ULTIK:
                    scott.body.applyLinearImpulse(new Vec2(-90f, -100f), scott.body.getPosition());
                    scott.state = Scott.State.IDLE;
                    break;
                case LULTIK:
                    scott.body.applyLinearImpulse(new Vec2(90f, -100f), scott.body.getPosition());
                    scott.state = Scott.State.LIDLE;
                    break;
            }


            System.out.println("Contact = " + scott.contacted);
        }



    public static void contact1(Contact contact, Scott scott){

        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if(scott.body==a && (Gameplay01.bodies.get(b))!= "ground"){
            scott.contacted = true;
            scott.other = contact.getFixtureB().getBody();
        }else if(scott.body==b && (Gameplay01.bodies.get(a))!= "ground"){
            scott.contacted = true;
            scott.other = contact.getFixtureA().getBody();
        }

        switch (scott.state) {
            case JUMP:
                scott.state = Scott.State.IDLE;
                break;
            case LJUMP:
                scott.state = Scott.State.LIDLE;
                break;
            case JKICK:
                scott.body.applyLinearImpulse(new Vec2(-70f, -100f), scott.body.getPosition());
                scott.state = Scott.State.IDLE;
                break;
            case LJKICK:
                scott.body.applyLinearImpulse(new Vec2(70f, -100f), scott.body.getPosition());
                scott.state = Scott.State.LIDLE;
                break;
            case ULTIK:
                scott.body.applyLinearImpulse(new Vec2(-90f, -100f), scott.body.getPosition());
                scott.state = Scott.State.IDLE;
                break;
            case LULTIK:
                scott.body.applyLinearImpulse(new Vec2(90f, -100f), scott.body.getPosition());
                scott.state = Scott.State.LIDLE;
                break;
        }

            System.out.println("Contact = " + scott.contacted);


    }

    public static void contact2(Contact contact, Scott scott){

        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();


        if(scott.body==a && (Gameplay02.bodies.get(b))!= "ground"){
            scott.contacted = true;
            scott.other = contact.getFixtureB().getBody();
        }else if(scott.body==b && (Gameplay02.bodies.get(a))!= "ground"){
            scott.contacted = true;
            scott.other = contact.getFixtureA().getBody();
        }

        switch (scott.state) {
            case JUMP:
                scott.state = Scott.State.IDLE;
                break;
            case LJUMP:
                scott.state = Scott.State.LIDLE;
                break;
            case JKICK:
                scott.body.applyLinearImpulse(new Vec2(-70f, -100f), scott.body.getPosition());
                scott.state = Scott.State.IDLE;
                break;
            case LJKICK:
                scott.body.applyLinearImpulse(new Vec2(70f, -100f), scott.body.getPosition());
                scott.state = Scott.State.LIDLE;
                break;
            case ULTIK:
                scott.body.applyLinearImpulse(new Vec2(-90f, -100f), scott.body.getPosition());
                scott.state = Scott.State.IDLE;
                break;
            case LULTIK:
                scott.body.applyLinearImpulse(new Vec2(90f, -100f), scott.body.getPosition());
                scott.state = Scott.State.LIDLE;
                break;
        }
            System.out.println("Contact = " + scott.contacted);
        }

    }

