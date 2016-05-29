package spark.game01.core.ContactObject;


import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import spark.game01.core.character.Gideon_char.Gideon;
import spark.game01.core.character.Scott_char.Scott;

import java.util.HashMap;

public class ContactMotion3 {

    public ContactMotion3(final World world, final HashMap bodies, final Scott scott, final Gideon gideon){
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();


                if(bodies.get(a)=="ground"&&b==scott.body || bodies.get(b)=="ground"&&a==scott.body){
                    ContactScott.contact2(contact,scott);
                }
                if(bodies.get(a)=="ground"&&b==gideon.body || bodies.get(b)=="ground"&&a==gideon.body){
                    ContactGideon.contact(contact,scott,gideon);
                }

                if((a==scott.body&&b==gideon.body) || (b==scott.body&&a==gideon.body)){
                    ContactScott.contact2(contact,scott);
                    ContactGideon.contact(contact,scott,gideon);
                }
            }

            @Override
            public void endContact(Contact contact) {

                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                if((a==scott.body&&b==gideon.body) || (b==scott.body&&a==gideon.body)){
                    scott.contacted = false;
                    gideon.contacted = false;
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
