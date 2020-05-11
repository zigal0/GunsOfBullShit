package project;

import javafx.scene.media.AudioClip;

public class Sounds {
    final AudioClip ak47 = new AudioClip(getClass()
            .getResource("sounds/ak47.wav").toExternalForm());
    final AudioClip explode = new AudioClip(getClass()
            .getResource("sounds/explode.wav").toExternalForm());
    final AudioClip turret = new AudioClip(getClass()
            .getResource("sounds/turret.wav").toExternalForm());
    final AudioClip die = new AudioClip(getClass()
            .getResource("sounds/die.wav").toExternalForm());
    final AudioClip unstoppable = new AudioClip(getClass()
            .getResource("sounds/unstoppable.wav").toExternalForm());
    final AudioClip prepare = new AudioClip(getClass()
            .getResource("sounds/prepare.wav").toExternalForm());
    final AudioClip hit = new AudioClip(getClass()
            .getResource("sounds/hit.wav").toExternalForm());
    final AudioClip ric = new AudioClip(getClass()
            .getResource("sounds/ric.wav").toExternalForm());
    final AudioClip ricmetal = new AudioClip(getClass()
            .getResource("sounds/ricmetal.wav").toExternalForm());
    final AudioClip explosion = new AudioClip(getClass()
            .getResource("sounds/explosion.wav").toExternalForm());
    final AudioClip m4a1 = new AudioClip(getClass()
            .getResource("sounds/m4a1.wav").toExternalForm());
    final AudioClip awp = new AudioClip(getClass()
            .getResource("sounds/awp.wav").toExternalForm());
    final AudioClip deagle = new AudioClip(getClass()
            .getResource("sounds/deagle.wav").toExternalForm());
    final AudioClip wpnHudOn = new AudioClip(getClass()
            .getResource("sounds/wpnHudOn.wav").toExternalForm());
    final AudioClip wpnSelect = new AudioClip(getClass()
            .getResource("sounds/wpnSelect.wav").toExternalForm());

    public Sounds() {
        this.setVolume();
    }

    public void wpnHudOn() {
        wpnHudOn.play();
    }

    public void wpnSelect() {
        wpnSelect.play();
    }

    public void deagle() {
        deagle.play();
    }

    public void awp() {
        awp.play();
    }

    public void m4a1() {
        m4a1.play();
    }

    public void explosion() {
        explosion.play();
    }

    public void hit() {
        hit.play();
    }

    public void ric() {
        ric.play();
    }

    public void ricmetal() {
        ricmetal.play();
    }

    public void turret() {
        turret.play();
    }

    public void ak47() {
        ak47.play();
    }

    public void explode() {
        explode.play();
    }

    public void die() {
        die.play();
    }

    public void unstoppable() {
        unstoppable.play();
    }

    public void prepare() {
        prepare.play();
    }

    private void setVolume() {
//        ak47.setVolume(0.1);
//        turret.setVolume(0.1);
//        explode.setVolume(0.1);
//        die.setVolume(0.1);
    }
}
