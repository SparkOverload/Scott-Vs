package spark.game01.core.ContactObject;


import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import spark.game01.core.Screen.Gameplay00;
import spark.game01.core.character.Scott_char.Scott;
import spark.game01.core.character.Tom_char.Tom;

import java.util.HashMap;

public class ContactMotion1 {

    public ContactMotion1(final World world, final HashMap bodies, final Scott scott, final Tom tom){
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();



                if(bodies.get(a)=="ground"&&b==scott.body || bodies.get(b)=="ground"&&a==scott.body){
                    ContactScott.contact(contact,scott);
                }
                if(bodies.get(a)=="ground"&&b==tom.body || bodies.get(b)=="ground"&&a==tom.body){
                    ContactTom.contact(contact,scott,tom);
                }

                if((a==scott.body&&b==tom.body) || (b==scott.body&&a==tom.body)){
                    ContactScott.contact(contact,scott);
                    ContactTom.contact(contact,scott,tom);
                }

                if(bodies.get(a)=="ground1"&&b==scott.body || bodies.get(b)=="ground1"&&a==scott.body){
                    scott.state= Scott.State.LOSE;
                    Gameplay00.score=0;
                }else if(bodies.get(a)=="ground1"&&b==tom.body || bodies.get(b)=="ground1"&&a==tom.body){
                    Gameplay00.scoret=0;
                }
            }

            @Override
            public void endContact(Contact contact) {

                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                if((a==scott.body&&b==tom.body) || (b==scott.body&&a==tom.body)){
                    scott.contacted = false;
                    tom.contacted = false;
                }

            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {

            }
        });
    }

}
