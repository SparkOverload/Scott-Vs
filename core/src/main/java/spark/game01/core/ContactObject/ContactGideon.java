package spark.game01.core.ContactObject;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;
import spark.game01.core.Screen.Gameplay02;
import spark.game01.core.character.Gideon_char.Gideon;
import spark.game01.core.character.Scott_char.Scott;

public class ContactGideon {
    public static void contact(Contact contact, Scott scott, Gideon gideon) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if (gideon.body == a && (Gameplay02.bodies.get(b)) != "ground") {
            gideon.contacted = true;
            gideon.other = contact.getFixtureB().getBody();
        } else if (gideon.body == b && (Gameplay02.bodies.get(a)) != "ground") {
            gideon.contacted = true;
            gideon.other = contact.getFixtureA().getBody();
        }

        switch (gideon.state) {
            case LWASATTK2:
                if ((gideon.body == a && (Gameplay02.bodies.get(b)) == "ground") ||
                        (gideon.body == b && (Gameplay02.bodies.get(a)) == "ground")) {
                    gideon.body.setActive(false);
                    gideon.i1 = 50;
                }
                break;
            case WASATTK2:
                if ((gideon.body == a && (Gameplay02.bodies.get(b)) == "ground") ||
                        (gideon.body == b && (Gameplay02.bodies.get(a)) == "ground")) {
                    gideon.body.setActive(false);
                    gideon.i1 = 50;
                }
                break;
        }


        // ###################  WasAttk   ######################
        if ((gideon.body == a && scott.body == b) ||
                (gideon.body == b && scott.body == a)) {
            if (scott.spriteIndex >= 189 && scott.spriteIndex <= 191) {   //lwasattk2
                if (gideon.state == Gideon.State.IDLE) {
                    gideon.state = Gideon.State.WASATTK2;
                    Gameplay02.scoreg -= 10;
                } else if (gideon.state == Gideon.State.LIDLE) {
                    gideon.state = Gideon.State.LWASATTK2;
                    Gameplay02.scoreg -= 10;
                } else {
                    gideon.state = Gideon.State.LWASATTK2;
                    Gameplay02.scoreg -= 10;
                }

            }
            if (scott.spriteIndex >= 380 && scott.spriteIndex <= 382) {   //wasattk2
                if (gideon.state == Gideon.State.LIDLE) {
                    //gideon.body.applyLinearImpulse(new Vec2(5f, -10f), a.getPosition());
                    gideon.state = Gideon.State.LWASATTK2;
                    Gameplay02.scoreg -= 10;
                } else if (gideon.state == Gideon.State.IDLE) {
                    // gideon.body.applyLinearImpulse(new Vec2(-5f, -10f), a.getPosition());
                    gideon.state = Gideon.State.WASATTK2;
                    Gameplay02.scoreg -= 10;
                } else {
                    gideon.state = Gideon.State.WASATTK2;
                    // gideon.body.applyLinearImpulse(new Vec2(-5f, -10f), a.getPosition());
                    Gameplay02.scoreg -= 10;
                }
            }
            if (scott.spriteIndex >= 192 && scott.spriteIndex <= 201) {     //wasHeadputt
                //gideon.body.applyLinearImpulse(new Vec2(5f, -10f), a.getPosition());
                gideon.state = Gideon.State.LWASATTK2;
                Gameplay02.scoreg -= 10;
            }
            if (scott.spriteIndex >= 384 && scott.spriteIndex <= 393) {     //wasHeadputt
                //gideon.body.applyLinearImpulse(new Vec2(-5f, -10f), a.getPosition());
                gideon.state = Gideon.State.WASATTK2;
                Gameplay02.scoreg -= 10;
            }
            if (scott.spriteIndex >= 137 && scott.spriteIndex <= 143) {
                //gideon.body.applyLinearImpulse(new Vec2(5f, -10f), a.getPosition());
                gideon.state = Gideon.State.LWASATTK2;
                Gameplay02.scoreg -= 10;
            }
            if (scott.spriteIndex >= 329 && scott.spriteIndex <= 335) {
                //gideon.body.applyLinearImpulse(new Vec2(-5f, -10f), a.getPosition());
                gideon.state = Gideon.State.WASATTK2;
                Gameplay02.scoreg -= 10;
            }
            if (scott.spriteIndex >= 147 && scott.spriteIndex <= 152) {
                //gideon.body.applyLinearImpulse(new Vec2(5f, -10f), a.getPosition());
                gideon.state = Gideon.State.LWASATTK2;
                Gameplay02.scoreg -= 10;
            }
            if (scott.spriteIndex >= 339 && scott.spriteIndex <= 344) {
                //gideon.body.applyLinearImpulse(new Vec2(-5f, -10f), a.getPosition());
                gideon.state = Gideon.State.WASATTK2;
                Gameplay02.scoreg -= 10;
            }
            if (scott.spriteIndex >= 167 && scott.spriteIndex <= 170) {
                //gideon.body.applyLinearImpulse(new Vec2(5f, -10f), a.getPosition());
                gideon.state = Gideon.State.LWASATTK2;
                Gameplay02.scoreg -= 10;
            }
            if (scott.spriteIndex >= 359 && scott.spriteIndex <= 361) {
                // gideon.body.applyLinearImpulse(new Vec2(-5f, -10f), a.getPosition());
                gideon.state = Gideon.State.WASATTK2;
                Gameplay02.scoreg -= 10;
            }
            if (scott.spriteIndex >= 111 && scott.spriteIndex <= 122) {
                //gideon.body.applyLinearImpulse(new Vec2(5f, -10f), a.getPosition());
                gideon.state = Gideon.State.LWASATTK2;
                Gameplay02.scoreg -= 10;
            }
            if (scott.spriteIndex >= 286 && scott.spriteIndex <= 297) {
                //gideon.body.applyLinearImpulse(new Vec2(-5f, -10f), a.getPosition());
                gideon.state = Gideon.State.WASATTK2;
                Gameplay02.scoreg -= 10;
            }
            // ###################  WasAttk   ######################

        }
    }
}
