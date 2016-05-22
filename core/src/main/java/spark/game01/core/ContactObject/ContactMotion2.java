package spark.game01.core.ContactObject;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import spark.game01.core.character.Matt_char.Matt;
import spark.game01.core.character.Scott_char.Scott;
import spark.game01.core.character.Tom_char.Tom;

import java.util.HashMap;

/**
 * Created by SPARK.O on 5/22/2016.
 */
public class ContactMotion2 {

    public ContactMotion2(final World world, final HashMap bodies, final Scott scott, final Matt matt){
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();

                //System.out.println(bodies.get(a)+" contact "+bodies.get(b));

                if(bodies.get(a)=="ground"&&b==scott.body || bodies.get(b)=="ground"&&a==scott.body){
                    ContactScott.contact(contact,scott);
                }
                if(bodies.get(a)=="ground"&&b==matt.body || bodies.get(b)=="ground"&&a==matt.body){
                    ContactMatt.contact(contact,scott,matt);
                }

                if((a==scott.body&&b==matt.body) || (b==scott.body&&a==matt.body)){
                    ContactScott.contact(contact,scott);
                    ContactMatt.contact(contact,scott,matt);
                }
            }

            @Override
            public void endContact(Contact contact) {

                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                if((a==scott.body&&b==matt.body) || (b==scott.body&&a==matt.body)){
                    scott.contacted = false;
                    matt.contacted = false;
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
