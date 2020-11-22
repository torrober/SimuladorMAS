
import javax.swing.JOptionPane;
import processing.core.*;
import controlP5.*;
import grafica.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class App extends PApplet {

    // Basado en la plantilla de Processing hecha por DGXProjects29 
    // https://github.com/DgxProjects29/JavaProcessingSimpleTemplate
    PImage bg, layer2, layer3, layer4, bg2, op1, op2, op3, spring, bg3, overlay, graph, back;
    PFont font, inputFont;
    int xPos = 0, lastxPos = 1, lastheight = 1;
    GPlot plot;
    GPointsArray points;
    ControlP5 cp5;
    boolean esAmortiguado = false, esSubAmortiguado = false, graficos = false;
    int slider1X = 0, slider1Y = 0, slider3, slider4;
    int i, f = 0, o, a, start_time;
    float fade, masa = (float) 15, deltaY, y = 200, initY = 200, b = (float) 0, k = (float) 0.90, vel = (float) 1, acc, g = (float) 0.98, fuerza = 0, w = 0, t;
    int c = 1;
    float[] yValues;

    @Override
    public void settings() {
        size(800, 600);
    }

    @Override
    public void setup() {
        fill(0);
        bg = loadImage("lab_alvarobarrios_dorianoojeda_robertorocha/data/layer1.png");
        layer2 = loadImage("lab_alvarobarrios_dorianoojeda_robertorocha/data/layer2.png");
        layer3 = loadImage("lab_alvarobarrios_dorianoojeda_robertorocha/data/layer3.png");
        layer4 = loadImage("lab_alvarobarrios_dorianoojeda_robertorocha/data/layer4.png");
        bg2 = loadImage("lab_alvarobarrios_dorianoojeda_robertorocha/data/screen_two_bg.jpg");
        bg3 = loadImage("lab_alvarobarrios_dorianoojeda_robertorocha/data/bg3.jpg");
        op1 = loadImage("lab_alvarobarrios_dorianoojeda_robertorocha/data/screen_two_op1.png");
        op2 = loadImage("lab_alvarobarrios_dorianoojeda_robertorocha/data/screen_two_op2.png");
        op3 = loadImage("lab_alvarobarrios_dorianoojeda_robertorocha/data/screen_two_op3.png");
        graph = loadImage("lab_alvarobarrios_dorianoojeda_robertorocha/data/graph_button.png");
        font = createFont("lab_alvarobarrios_dorianoojeda_robertorocha/data/BAHNSCHRIFT.TTF", 32);
        inputFont = createFont("lab_alvarobarrios_dorianoojeda_robertorocha/data/BAHNSCHRIFT.TTF", 14);
        spring = loadImage("lab_alvarobarrios_dorianoojeda_robertorocha/data/spring_lab.png");
        overlay = loadImage("lab_alvarobarrios_dorianoojeda_robertorocha/data/overlay.png");
        back = loadImage("lab_alvarobarrios_dorianoojeda_robertorocha/data/back.png");
        i = 360;
        cp5 = new ControlP5(this);
        cp5.addButton("saveFile").setVisible(false);
        cp5.addButton("customValues").setVisible(false);
        cp5.addButton("setCustomValues").setVisible(false);
        cp5.addSlider("masa").setRange(15, 100).setVisible(false);
        cp5.addSlider("g").setRange((float) 0.10, (float) 2.47).setVisible(false);
        cp5.addSlider("k").setRange((float) 0.1, 40).setVisible(false);
        cp5.addSlider("b").setRange((float) 1, (float) 10).setVisible(false);
        cp5.addSlider("initY").setRange((float) 1, 500).setVisible(false);
        cp5.addSlider("fuerza").setRange((float) 2, 5).setVisible(false);
        cp5.addSlider("w").setRange((float) 2, 5).setVisible(false);
        cp5.addTextfield("masa_value").setVisible(false).setColor(0).setFont(inputFont);
        cp5.addTextfield("g_value").setVisible(false).setColor(0).setFont(inputFont);
        cp5.addTextfield("k_value").setVisible(false).setColor(0).setFont(inputFont);
        cp5.addTextfield("b_value").setVisible(false).setColor(0).setFont(inputFont);
        cp5.addTextfield("initY_value").setVisible(false).setColor(0).setFont(inputFont);
        cp5.addTextfield("fuerza_value").setVisible(false).setColor(0).setFont(inputFont);
        cp5.addTextfield("w_value").setVisible(false).setColor(0).setFont(inputFont);
    }

    public void firstScreen() {
        i--;
        if (i <= 1) {
            i = 0;
        }
        f++;
        if (f >= 254) {
            f = 254;
        }

        if (fade > 99) {
            fade = mouseX + mouseY / f;
        } else if (fade < 99) {
            fade = 99;

        }
        noStroke();
        background(bg);
        tint(255, f);
        if (mouseY >= 450 && mouseY <= 550 && mouseX >= 280 && mouseX <= 500 && i == 0) {
            image(layer2, 0, 0);
            image(layer3, 0, -i * k);
            //tint(255, fade);
            image(layer4, 0, i);
            cursor(HAND);
            if (mousePressed == true) {
                a = 1;
            }
        } else {
            image(layer2, 0, 0);
            image(layer3, 0, -i);
            tint(255, fade);
            image(layer4, 0, i);
            cursor(ARROW);
        }
    }

    public void secondScreen() {
        cp5.getController("masa").hide();
        cp5.getController("g").hide();
        cp5.getController("k").hide();
        cp5.getController("b").hide();
        cp5.getController("initY").hide();
        cp5.getController("fuerza").hide();
        cp5.getController("w").hide();
        cp5.getController("customValues").hide();
        cp5.getController("saveFile").hide();
        background(bg2);
        textSize(32);
        fill(255, 255, 255);
        textFont(font);
        text("Seleccione el tipo de sistema a emular", width / 2, 100);
        textAlign(CENTER);
        i = 255;
        f = 0;
        if (mouseY >= 164 && mouseY <= 431 && mouseX >= 57 && mouseX <= 254) {
            cursor(HAND);
            if (mousePressed) {
                a = 2;
            }
            image(op1, 0, 0);
            tint(255, 100);
            image(op2, 0, 0);
            image(op3, 0, 0);
            tint(255, 255);
        } else if (mouseY >= 164 && mouseY <= 431 && mouseX >= 298 && mouseX <= 494) {
            cursor(HAND);
            if (mousePressed) {
                a = 3;
            }
            image(op1, 0, 0);
            tint(255, 255);
            image(op2, 0, 0);
            tint(255, 100);
            image(op3, 0, 0);
        } else if (mouseY >= 164 && mouseY <= 431 && mouseX >= 543 && mouseX <= 738) {
            cursor(HAND);
            if (mousePressed) {
                a = 6;
            }
            tint(255, 100);
            image(op1, 0, 0);
            image(op2, 0, 0);
            tint(255, 255);
            image(op3, 0, 0);
        } else {
            cursor(ARROW);
            tint(255, 100);
            image(op1, 0, 0);
            tint(255, 100);
            image(op2, 0, 0);
            image(op3, 0, 0);
        }
    }

    public void optionOne() {
        cp5.getController("masa_value").hide();
        cp5.getController("g_value").hide();
        cp5.getController("k_value").hide();
        cp5.getController("b_value").hide();
        cp5.getController("initY_value").hide();
        cp5.getController("fuerza_value").hide();
        cp5.getController("w_value").hide();        
        cp5.getController("setCustomValues").hide();
        esAmortiguado = true;
        textFont(font);
        textAlign(LEFT);
        slider1X = 140;
        slider1Y = 100;
        background(bg3);
        fill(255, 255, 255);
        cursor(ARROW);
        imageMode(CORNERS);
        image(spring, 566 - 40, 0, 566 + 40, y);
        circle(566, y, 60 + ((int) masa - 15));
        deltaY = y - initY;
        text(String.format("%.2f", masa) + "kg", 520, y + 60 + (masa - 15));
        textSize(20);
        acc = g - (float) (k * deltaY / masa) - (float) (b * vel / masa);
        vel += acc;
        y += vel;
        //acc = 0;
        text("Masa - " + String.format("%.2f", masa) + "kg", 48, 54);
        textSize(32);
        cp5.getController("masa").setPosition(48, 66);
        cp5.getController("masa").setHeight(30);
        cp5.getController("masa").setWidth(228);
        cp5.getController("masa").show();
        cp5.getController("masa").setLabelVisible(false);
        cp5.getController("masa").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("masa").setColorForeground(color(255, 255, 255, 200));
        cp5.getController("masa").setColorActive(color(255, 255, 255));
        textSize(20);
        text("Gravedad - " + String.format("%.1f", g * 10) + "m/s²", 48, 64 + 56);
        cp5.getController("g").setPosition(48, 66 + 64);
        cp5.getController("g").setHeight(30);
        cp5.getController("g").setWidth(228);
        cp5.getController("g").show();
        cp5.getController("g").setLabelVisible(false);
        cp5.getController("g").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("g").setColorForeground(color(255, 255, 255, 200));
        cp5.getController("g").setColorActive(color(255, 255, 255));
        text("Constante K - " + String.format("%.1f", k) + "N/m", 48, 120 + 62);
        cp5.getController("k").setPosition(48, 130 + 64);
        cp5.getController("k").setHeight(30);
        cp5.getController("k").setWidth(228);
        cp5.getController("k").show();
        cp5.getController("k").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("k").setColorForeground(color(255, 255, 255, 200));
        cp5.getController("k").setColorActive(color(255, 255, 255));
        cp5.getController("k").setLabelVisible(false);
        text("Elongación del resorte", 48, 182 + 64);
        cp5.getController("initY").setPosition(48, 194 + 64);
        cp5.getController("initY").setHeight(30);
        cp5.getController("initY").setWidth(228);
        cp5.getController("initY").show();
        cp5.getController("initY").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("initY").setColorForeground(color(255, 255, 255, 200));
        cp5.getController("initY").setColorActive(color(255, 255, 255));
        cp5.getController("initY").setLabelVisible(false);
        text("Amortiguación del resorte", 48, 310);
        cp5.getController("b").setPosition(48, 310 + 8);
        cp5.getController("b").setHeight(30);
        cp5.getController("b").setWidth(228);
        cp5.getController("b").show();
        cp5.getController("b").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("b").setColorForeground(color(255, 255, 255, 200));
        cp5.getController("b").setColorActive(color(255, 255, 255));
        cp5.getController("b").setLabelVisible(false);
        cp5.getController("customValues").setPosition(48, 310 + 66);
        cp5.getController("customValues").show();
        cp5.getController("customValues").setCaptionLabel("editar Datos");
        cp5.getController("customValues").getCaptionLabel().setFont(font);
        cp5.getController("customValues").setSize(228, 60);
        cp5.getController("customValues").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("customValues").setColorForeground(color(0, 0, 0, 200));
        cp5.getController("customValues").setColorActive(color(0, 0, 0, 230));
        //cp5.getController("customValues").setCursor(ARROW);
        cp5.getController("saveFile").setPosition(48, 376 + 85);
        cp5.getController("saveFile").show();
        cp5.getController("saveFile").setCaptionLabel("guardar archivo");
        cp5.getController("saveFile").getCaptionLabel().setFont(createFont("Bahnschrift", 24));
        cp5.getController("saveFile").setSize(228, 60);
        cp5.getController("saveFile").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("saveFile").setColorForeground(color(0, 0, 0, 200));
        cp5.getController("saveFile").setColorActive(color(0, 0, 0, 230));
        image(back, 0, 0);
        if (mouseX >= 351 && mouseX < 383 && mouseY >= 14 && mouseY < 56) {
            if (mousePressed) {
                a = 1;
            }
        }
    }

    public void optionTwo() {
        cp5.getController("masa_value").hide();
        cp5.getController("g_value").hide();
        cp5.getController("k_value").hide();
        cp5.getController("initY_value").hide();
        cp5.getController("b_value").hide();
        cp5.getController("fuerza_value").hide();
        cp5.getController("w_value").hide();        
        cp5.getController("setCustomValues").hide();
        esAmortiguado = false;
        esSubAmortiguado = false;
        b = 0;
        textFont(font);
        textAlign(LEFT);
        slider1X = 140;
        slider1Y = 100;
        background(bg3);
        fill(255, 255, 255);
        cursor(ARROW);
        tint(255);
        imageMode(CORNERS);
        image(spring, 566 - 40, 0, 566 + 40, y);
        circle(566, y, 60 + ((int) masa - 15));
        deltaY = y - initY;
        text(String.format("%.2f", masa) + "kg", 520, y + 60 + (masa - 15));
        textSize(20);
        acc = g - (float) (k * deltaY / masa) - (float) (b * vel / masa);
        vel += acc;
        y += vel;
        acc = 0;
        //text();
        text("Masa - " + String.format("%.2f", masa) + "kg", 48, 54);
        textSize(32);
        cp5.getController("masa").setPosition(48, 66);
        cp5.getController("masa").setHeight(30);
        cp5.getController("masa").setWidth(228);
        cp5.getController("masa").show();
        cp5.getController("masa").setLabelVisible(false);
        cp5.getController("masa").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("masa").setColorForeground(color(255, 255, 255, 200));
        cp5.getController("masa").setColorActive(color(255, 255, 255));
        textSize(20);
        text("Gravedad - " + String.format("%.1f", g * 10) + "m/s²", 48, 64 + 56);
        cp5.getController("g").setPosition(48, 66 + 64);
        cp5.getController("g").setHeight(30);
        cp5.getController("g").setWidth(228);
        cp5.getController("g").show();
        cp5.getController("g").setLabelVisible(false);
        cp5.getController("g").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("g").setColorForeground(color(255, 255, 255, 200));
        cp5.getController("g").setColorActive(color(255, 255, 255));
        text("Constante K - " + String.format("%.1f", k) + "N/m", 48, 120 + 62);
        cp5.getController("k").setPosition(48, 130 + 64);
        cp5.getController("k").setHeight(30);
        cp5.getController("k").setWidth(228);
        cp5.getController("k").show();
        cp5.getController("k").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("k").setColorForeground(color(255, 255, 255, 200));
        cp5.getController("k").setColorActive(color(255, 255, 255));
        cp5.getController("k").setLabelVisible(false);
        text("Elongación del resorte", 48, 182 + 64);
        cp5.getController("initY").setPosition(48, 194 + 64);
        cp5.getController("initY").setHeight(30);
        cp5.getController("initY").setWidth(228);
        cp5.getController("initY").show();
        cp5.getController("initY").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("initY").setColorForeground(color(255, 255, 255, 200));
        cp5.getController("initY").setColorActive(color(255, 255, 255));
        cp5.getController("initY").setLabelVisible(false);
        //System.out.println(mouseX); //328-447
        cp5.getController("customValues").setPosition(48, 310);
        cp5.getController("customValues").show();
        //cp5.getController("customValues").setLabelVisible(false);
        cp5.getController("customValues").setCaptionLabel("editar Datos");
        cp5.getController("customValues").getCaptionLabel().setFont(font);
        cp5.getController("customValues").setSize(228, 60);
        cp5.getController("customValues").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("customValues").setColorForeground(color(0, 0, 0, 200));
        cp5.getController("customValues").setColorActive(color(0, 0, 0, 230));
        cp5.getController("saveFile").setPosition(48, 330 + 60);
        cp5.getController("saveFile").show();
        cp5.getController("saveFile").setCaptionLabel("guardar archivo");
        cp5.getController("saveFile").getCaptionLabel().setFont(createFont("Bahnschrift", 24));
        cp5.getController("saveFile").setSize(228, 60);
        cp5.getController("saveFile").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("saveFile").setColorForeground(color(0, 0, 0, 200));
        cp5.getController("saveFile").setColorActive(color(0, 0, 0, 230));
        image(back, 0, 0);
        if (mouseX >= 351 && mouseX < 383 && mouseY >= 14 && mouseY < 56) {
            if (mousePressed) {
                a = 1;
            }
        }
    }

    public void optionThree() {
        cp5.getController("masa_value").hide();
        cp5.getController("g_value").hide();
        cp5.getController("k_value").hide();
        cp5.getController("b_value").hide();
        cp5.getController("initY_value").hide();
        cp5.getController("fuerza_value").hide();
        cp5.getController("w_value").hide();
        cp5.getController("setCustomValues").hide();
        esAmortiguado = true;
        esSubAmortiguado = true;
        textFont(font);
        textAlign(LEFT);
        slider1X = 140;
        slider1Y = 100;
        background(bg3);
        fill(255, 255, 255);
        cursor(ARROW);
        imageMode(CORNERS);
        image(spring, 566 - 40, 0, 566 + 40, y);
        circle(566, y, 60 + ((int) masa - 15));
        deltaY = y - initY;
        text(String.format("%.2f", masa) + "kg", 520, y + 60 + (masa - 15));
        textSize(20);
        t = millis() / 100;
        acc = g - (float) (k * deltaY / masa) - (float) (b * vel / masa) + (float) (fuerza * cos(w * t));
        vel += acc;
        y += vel;
        acc = 0;
        text("Masa - " + String.format("%.2f", masa) + "kg", 48, 30);
        textSize(32);
        cp5.getController("masa").setPosition(48, 40);
        cp5.getController("masa").setHeight(30);
        cp5.getController("masa").setWidth(228);
        cp5.getController("masa").show();
        cp5.getController("masa").setLabelVisible(false);
        cp5.getController("masa").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("masa").setColorForeground(color(255, 255, 255, 200));
        cp5.getController("masa").setColorActive(color(255, 255, 255));
        textSize(20);
        text("Gravedad - " + String.format("%.1f", g * 10) + "m/s²", 48, 64 + 25);
        cp5.getController("g").setPosition(48, 66 + 30);
        cp5.getController("g").setHeight(30);
        cp5.getController("g").setWidth(228);
        cp5.getController("g").show();
        cp5.getController("g").setLabelVisible(false);
        cp5.getController("g").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("g").setColorForeground(color(255, 255, 255, 200));
        cp5.getController("g").setColorActive(color(255, 255, 255));
        text("Constante K - " + String.format("%.1f", k) + "N/m", 48, 120 + 30);
        cp5.getController("k").setPosition(48, 130 + 30);
        cp5.getController("k").setHeight(30);
        cp5.getController("k").setWidth(228);
        cp5.getController("k").show();
        cp5.getController("k").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("k").setColorForeground(color(255, 255, 255, 200));
        cp5.getController("k").setColorActive(color(255, 255, 255));
        cp5.getController("k").setLabelVisible(false);
        text("Elongación del resorte", 48, 182 + 30);
        cp5.getController("initY").setPosition(48, 194 + 30);
        cp5.getController("initY").setHeight(30);
        cp5.getController("initY").setWidth(228);
        cp5.getController("initY").show();
        cp5.getController("initY").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("initY").setColorForeground(color(255, 255, 255, 200));
        cp5.getController("initY").setColorActive(color(255, 255, 255));
        cp5.getController("initY").setLabelVisible(false);
        text("Amortiguación del resorte", 48, 250 + 30);
        cp5.getController("b").setPosition(48, 250 + 30 + 8);
        cp5.getController("b").setHeight(30);
        cp5.getController("b").setWidth(228);
        cp5.getController("b").show();
        cp5.getController("b").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("b").setColorForeground(color(255, 255, 255, 200));
        cp5.getController("b").setColorActive(color(255, 255, 255));
        cp5.getController("b").setLabelVisible(false);
        text("Fuerza impulsora", 48, 280 + 60);
        cp5.getController("fuerza").setPosition(48, 340 + 8);
        cp5.getController("fuerza").setHeight(30);
        cp5.getController("fuerza").setWidth(228);
        cp5.getController("fuerza").show();
        cp5.getController("fuerza").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("fuerza").setColorForeground(color(255, 255, 255, 200));
        cp5.getController("fuerza").setColorActive(color(255, 255, 255));
        cp5.getController("fuerza").setLabelVisible(false);
        text("Frecuencia impulsora", 48, 340 + 66);
        cp5.getController("w").setPosition(48, 340 + 66 + 8);
        cp5.getController("w").setHeight(30);
        cp5.getController("w").setWidth(228);
        cp5.getController("w").show();
        cp5.getController("w").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("w").setColorForeground(color(255, 255, 255, 200));
        cp5.getController("w").setColorActive(color(255, 255, 255));
        cp5.getController("w").setLabelVisible(false);
        cp5.getController("customValues").setPosition(48, 442 + 15);
        cp5.getController("customValues").show();
        cp5.getController("customValues").setCaptionLabel("editar Datos");
        cp5.getController("customValues").getCaptionLabel().setFont(font);
        cp5.getController("customValues").setSize(228, 60);
        cp5.getController("customValues").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("customValues").setColorForeground(color(0, 0, 0, 200));
        cp5.getController("customValues").setColorActive(color(0, 0, 0, 230));
        //cp5.getController("customValues").setCursor(ARROW);
        cp5.getController("saveFile").setPosition(48, 442 + 85);
        cp5.getController("saveFile").show();
        cp5.getController("saveFile").setCaptionLabel("guardar archivo");
        cp5.getController("saveFile").getCaptionLabel().setFont(createFont("Bahnschrift", 24));
        cp5.getController("saveFile").setSize(228, 60);
        cp5.getController("saveFile").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("saveFile").setColorForeground(color(0, 0, 0, 200));
        cp5.getController("saveFile").setColorActive(color(0, 0, 0, 230));
        image(back, 0, 0);
        if (mouseX >= 351 && mouseX < 383 && mouseY >= 14 && mouseY < 56) {
            if (mousePressed) {
                a = 1;
            }
        }
    }

    public void customValues() {
        a = 4;
        background(bg3);
        image(spring, 566 - 40, 0, 566 + 40, y);
        fill(0, 0, 0, 100);
        rect(1, 1, 800, 600);
        noStroke();
        image(overlay, 30, 30);
        tint(255);
        cp5.getController("g").hide();
        cp5.getController("k").hide();
        cp5.getController("masa").hide();
        cp5.getController("initY").hide();
        cp5.getController("customValues").hide();
        cp5.getController("saveFile").hide();
        cp5.getController("fuerza").hide();
        cp5.getController("w").hide();
        fill(0);
        if (mouseX >= 480 && mouseX < 725 && mouseY > 295 && mouseY < 353) {
            cursor(HAND);
            if (mousePressed) {
                loadFile();
            }
        } else {
            cursor(ARROW);
        }
        if (mouseY > 30 && mouseY < 111 && mouseX > 676 && mouseX < 720) {
            if (mousePressed) {
                if (esAmortiguado && esSubAmortiguado == true) {
                    a = 6;
                } else if (esAmortiguado) {
                    a = 2;
                } else {
                    a = 3;
                }
            }
        }
        text("Masa (valor en kg)", 48 + 30, 54 + 30);
        textSize(32);
        cp5.getController("masa_value").setPosition(48 + 30, 66 + 30);
        cp5.getController("masa_value").setSize(228, 30);
        cp5.getController("masa_value").show();
        cp5.getController("masa_value").setLabel("");
        cp5.getController("masa_value").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("masa_value").setColorForeground(color(255, 255, 255, 200));
        cp5.getController("masa_value").setColorActive(color(255, 255, 255));
        textSize(20);
        text("Gravedad (valor en m/s²)", 48 + 30, 64 + 56 + 30);
        cp5.getController("g_value").setPosition(48 + 30, 66 + 64 + 30);
        cp5.getController("g_value").setSize(228, 30);
        cp5.getController("g_value").show();
        cp5.getController("g_value").setLabel("");
        cp5.getController("g_value").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("g_value").setColorForeground(color(255, 255, 255, 200));
        cp5.getController("g_value").setColorActive(color(255, 255, 255));
        text("Constante K - (valor en N/m)", 48 + 30, 120 + 62 + 30);
        cp5.getController("k_value").setPosition(48 + 30, 130 + 64 + 30);
        cp5.getController("k_value").setSize(228, 30);
        cp5.getController("k_value").show();
        cp5.getController("k_value").setColorBackground(color(0, 0, 0, 40));
        cp5.getController("k_value").setColorForeground(color(255, 255, 255, 200));
        cp5.getController("k_value").setColorActive(color(255, 255, 255));
        cp5.getController("k_value").setLabel("");
        if (esAmortiguado == true && esSubAmortiguado == false) {
            //text("Esta simulacion tiene amortiguacion", width/2, 200);
            cp5.getController("b").hide();
            text("Elongación del resorte", 48 + 30, 182 + 64 + 30);
            cp5.getController("initY_value").setPosition(48 + 30, 194 + 64 + 30);
            cp5.getController("initY_value").setSize(228, 30);
            cp5.getController("initY_value").show();
            cp5.getController("initY_value").setColorBackground(color(0, 0, 0, 40));
            cp5.getController("initY_value").setColorForeground(color(255, 255, 255, 200));
            cp5.getController("initY_value").setColorActive(color(255, 255, 255));
            cp5.getController("initY_value").setLabel("");
            text("Amortiguación del resorte", 48 + 30, 310 + 30);
            cp5.getController("b_value").setPosition(48 + 30, 310 + 8 + 30);
            cp5.getController("b_value").setSize(228, 30);
            cp5.getController("b_value").show();
            cp5.getController("b_value").setColorBackground(color(0, 0, 0, 40));
            cp5.getController("b_value").setColorForeground(color(255, 255, 255, 200));
            cp5.getController("b_value").setColorActive(color(255, 255, 255));
            cp5.getController("b_value").setLabel("");
            //System.out.println(mouseX); //328-447
            cp5.getController("setCustomValues").setPosition(48 + 30, 310 + 66 + 30);
            cp5.getController("setCustomValues").show();
            //cp5.getController("setCustomValues").setLabelVisible(false);
            cp5.getController("setCustomValues").setCaptionLabel("editar Datos");
            cp5.getController("setCustomValues").getCaptionLabel().setFont(font);
            cp5.getController("setCustomValues").setSize(228, 60);
            cp5.getController("setCustomValues").setColorBackground(color(0, 0, 0, 40));
            cp5.getController("setCustomValues").setColorForeground(color(0, 0, 0, 200));
            cp5.getController("setCustomValues").setColorActive(color(0, 0, 0, 230));
        } else if (esSubAmortiguado == true) {
            //text("Esta simulacion tiene amortiguacion", width/2, 200);
            cp5.getController("b").hide();
            text("Elongación del resorte", 48 + 30, 182 + 64 + 30);
            cp5.getController("initY_value").setPosition(48 + 30, 194 + 64 + 30);
            cp5.getController("initY_value").setSize(228, 30);
            cp5.getController("initY_value").show();
            cp5.getController("initY_value").setColorBackground(color(0, 0, 0, 40));
            cp5.getController("initY_value").setColorForeground(color(255, 255, 255, 200));
            cp5.getController("initY_value").setColorActive(color(255, 255, 255));
            cp5.getController("initY_value").setLabel("");
            text("Amortiguación del resorte", 48 + 30, 310 + 30);
            cp5.getController("b_value").setPosition(48 + 30, 310 + 8 + 30);
            cp5.getController("b_value").setSize(228, 30);
            cp5.getController("b_value").show();
            cp5.getController("b_value").setColorBackground(color(0, 0, 0, 40));
            cp5.getController("b_value").setColorForeground(color(255, 255, 255, 200));
            cp5.getController("b_value").setColorActive(color(255, 255, 255));
            cp5.getController("b_value").setLabel("");
            text("Fuerza impulsora", 48 + 30, 370 + 30);
            cp5.getController("fuerza_value").setPosition(48 + 30, 370 + 8 + 30);
            cp5.getController("fuerza_value").setSize(228, 30);
            cp5.getController("fuerza_value").show();
            cp5.getController("fuerza_value").setColorBackground(color(0, 0, 0, 40));
            cp5.getController("fuerza_value").setColorForeground(color(255, 255, 255, 200));
            cp5.getController("fuerza_value").setColorActive(color(255, 255, 255));
            cp5.getController("fuerza_value").setLabel("");
            text("Frecuencia impulsora", 48 + 30, 370 + 90);
            cp5.getController("w_value").setPosition(48 + 30, 370 + 8 + 90);
            cp5.getController("w_value").setSize(228, 30);
            cp5.getController("w_value").show();
            cp5.getController("w_value").setColorBackground(color(0, 0, 0, 40));
            cp5.getController("w_value").setColorForeground(color(255, 255, 255, 200));
            cp5.getController("w_value").setColorActive(color(255, 255, 255));
            cp5.getController("w_value").setLabel("");
            //System.out.println(mouseX); //328-447
            cp5.getController("setCustomValues").setPosition(48 + 30, 310 + 170 + 30);
            cp5.getController("setCustomValues").show();
            //cp5.getController("setCustomValues").setLabelVisible(false);
            cp5.getController("setCustomValues").setCaptionLabel("editar Datos");
            cp5.getController("setCustomValues").getCaptionLabel().setFont(createFont("Bahnschrift", 24));
            cp5.getController("setCustomValues").setSize(228, 40);
            cp5.getController("setCustomValues").setColorBackground(color(0, 0, 0, 40));
            cp5.getController("setCustomValues").setColorForeground(color(0, 0, 0, 200));
            cp5.getController("setCustomValues").setColorActive(color(0, 0, 0, 230));
        } else {
            text("Elongación del resorte", 48 + 30, 182 + 64 + 30);
            cp5.getController("initY_value").setPosition(48 + 30, 194 + 64 + 30);
            cp5.getController("initY_value").setSize(228, 30);
            cp5.getController("initY_value").show();
            cp5.getController("initY_value").setLabel("");
            cp5.getController("initY_value").setColorBackground(color(0, 0, 0, 40));
            cp5.getController("initY_value").setColorForeground(color(255, 255, 255, 200));
            cp5.getController("initY_value").setColorActive(color(255, 255, 255));
            cp5.getController("setCustomValues").setPosition(48 + 30, 310 + 30);
            cp5.getController("setCustomValues").show();
            //cp5.getController("setCustomValues").setLabelVisible(false);
            cp5.getController("setCustomValues").setCaptionLabel("editar Datos");
            cp5.getController("setCustomValues").getCaptionLabel().setFont(font);
            cp5.getController("setCustomValues").setSize(228, 60);
            cp5.getController("setCustomValues").setColorBackground(color(0, 0, 0, 40));
            cp5.getController("setCustomValues").setColorForeground(color(0, 0, 0, 200));
            cp5.getController("setCustomValues").setColorActive(color(0, 0, 0, 230));
        }
    }

    public void setCustomValues() {
        a = 4;

        float valor1 = parseFloat(cp5.get(Textfield.class,
                "masa_value").getText());

        float valor2 = parseFloat(cp5.get(Textfield.class,
                "g_value").getText());

        float valor3 = parseFloat(cp5.get(Textfield.class,
                "k_value").getText());

        float valor4 = parseFloat(cp5.get(Textfield.class,
                "initY_value").getText());

        float valor5 = parseFloat(cp5.get(Textfield.class,
                "b_value").getText());

        float valor6 = parseFloat(cp5.get(Textfield.class,
                "fuerza_value").getText());

        float valor7 = parseFloat(cp5.get(Textfield.class,
                "w_value").getText());
        System.out.println(valor2);
        //confirmacion de valores
        if (valor1 > 15 && valor1 < 100 && valor2 > 1 && valor2 < 24.7 && valor3 > 0.1 && valor3 < 40 && valor4 > 1 && valor4 < 500) {
            masa = valor1;
            g = valor2 / 10;
            k = valor3;
            initY = valor4;
            cp5.getController("masa").setValue(masa);
            cp5.getController("g").setValue(g);
            cp5.getController("k").setValue(k);
            cp5.getController("initY").setValue(initY);
            if (esAmortiguado == true && esSubAmortiguado == false) {
                if (valor5 > 1 && valor5 < 10) {
                    b = valor5;
                    cp5.getController("b").setValue(b);
                }
            } else if (esSubAmortiguado == true && esAmortiguado == true) {
                if (valor5 >= 1 && valor5 < 10 && valor6 >= 2 && valor6 < 5 && valor7 > 2 && valor7 <= 5) {
                    b = valor5;
                    cp5.getController("b").setValue(b);
                    fuerza = valor6;
                    cp5.getController("fuerza").setValue(fuerza);
                    w = valor7;
                    cp5.getController("w").setValue(w);
                }
            }
            JOptionPane.showMessageDialog(null, "Datos Validos", "Success", JOptionPane.INFORMATION_MESSAGE);
            if (esAmortiguado && esSubAmortiguado == false) {
                a = 2;
            } else if (esAmortiguado && esSubAmortiguado) {
                a = 6;
            } else {
                a = 3;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Datos Invalidos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadFile() {
        noLoop();
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".json", "json", "json");
        fc.setFileFilter(filter);
        JSONParser parser = new JSONParser();
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            System.out.println("File: " + file + " loaded");
            if (file.getName().endsWith("json")) {
                try {
                    Object obj = parser.parse(new FileReader(file));
                    JSONObject jsonObject = (JSONObject) obj;
                    float valor1 = ((Number) jsonObject.get("masa")).floatValue();
                    float valor2 = ((Number) jsonObject.get("initY")).floatValue();
                    float valor3 = ((Number) jsonObject.get("b")).floatValue();
                    boolean valor4 = (boolean) jsonObject.get("esAmortiguado");
                    float valor5 = ((Number) jsonObject.get("g")).floatValue();
                    float valor6 = ((Number) jsonObject.get("k")).floatValue();
                    boolean valor7 = (boolean) jsonObject.get("esSubAmortiguado");
                    float valor8 = ((Number) jsonObject.get("fuerza")).floatValue();
                    float valor9 = ((Number) jsonObject.get("w")).floatValue();
                    masa = valor1;
                    g = valor5 / 10;
                    k = valor6;
                    initY = valor2;
                    b = valor3;
                    fuerza = valor8;
                    w = valor9;
                    cp5.getController("masa").setValue(masa);
                    cp5.getController("g").setValue(g);
                    cp5.getController("k").setValue(k);
                    cp5.getController("initY").setValue(initY);
                    if (valor4 == true && valor7 == false) {
                        cp5.getController("b").setValue(b);
                        JOptionPane.showMessageDialog(null, "Archivo valido, contiene amortiguación", "Success", JOptionPane.INFORMATION_MESSAGE);
                        a = 2;
                    } else if (valor4 == true && valor7 == true) {
                        cp5.getController("b").setValue(b);
                        cp5.getController("fuerza").setValue(fuerza);
                        cp5.getController("w").setValue(w);
                        JOptionPane.showMessageDialog(null, "Archivo valido, contiene amortiguación y mov. forzado", "Success", JOptionPane.INFORMATION_MESSAGE);
                        a = 6;
                    } else {
                        JOptionPane.showMessageDialog(null, "Archivo valido", "Success", JOptionPane.INFORMATION_MESSAGE);
                        a = 3;
                    }
                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(null, "Archivo inexistente", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Archivo invalido", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Archivo invalido", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (NullPointerException e) {
                    JOptionPane.showMessageDialog(null, "Archivo invalido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Archivo invalido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        loop();
    }

    public void saveFile() {
        JSONObject obj = new JSONObject();
        obj.put("masa", masa);
        obj.put("g", g * 10);
        obj.put("k", k);
        obj.put("initY", initY);
        obj.put("b", b);
        obj.put("esAmortiguado", esAmortiguado);
        obj.put("esSubAmortiguado", esSubAmortiguado);
        obj.put("fuerza", fuerza);
        obj.put("w", w);
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".json", "json", "json");
        fc.setFileFilter(filter);
        fc.setSelectedFile(new File("datos_mas.json"));
        int option = fc.showSaveDialog(null);
        File f = fc.getSelectedFile();
        FileWriter fw;
        if (f.exists() && option == JFileChooser.APPROVE_OPTION) {
            int response = JOptionPane.showConfirmDialog(null, //
                    "Desea sobreescribir el archivo?", //
                    "Confirm", JOptionPane.YES_NO_OPTION, //
                    JOptionPane.QUESTION_MESSAGE);
            if (response != JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Operacion Abortada", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    fw = new FileWriter(f);
                    fw.write(obj.toString());
                    fw.close();
                    JOptionPane.showMessageDialog(null, "Archivo Guardado", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        } else if (option == JFileChooser.APPROVE_OPTION) {
            try {
                fw = new FileWriter(f);
                fw.write(obj.toString());
                fw.close();
                JOptionPane.showMessageDialog(null, "Archivo Guardado", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Operacion Abortada", "Error", JOptionPane.ERROR_MESSAGE);
        }
        //loop();
    }

    @Override
    public void draw() {
        switch (a) {
            case 0:
                firstScreen();
                break;
            case 1:
                secondScreen();
                break;
            case 2:
                optionOne();
                break;
            case 3:
                optionTwo();
                break;
            case 4:
                customValues();
                break;
            case 5:
                setCustomValues();
                break;
            default:
                firstScreen();
                break;
            case 6:
                optionThree();
                break;
        }
    }

    public static void main(String[] passedArgs) {
        String[] appletArgs = new String[]{App.class
            .getName()};
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}
