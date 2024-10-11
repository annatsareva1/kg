import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorConverterApp extends JFrame {

    private JSlider redSlider, greenSlider, blueSlider;
    private JSlider cmykCSlider, cmykMSlider, cmykYSlider, cmykKSlider;
    private JSlider hsvHSlider, hsvSSlider, hsvVSlider;

    private JTextField rgbR, rgbG, rgbB;
    private JTextField cmykC, cmykM, cmykY, cmykK;
    private JTextField hsvH, hsvS, hsvV;

    private JPanel colorPanel;

    public ColorConverterApp() {
        setTitle("Color Converter (RGB - CMYK - HSV)");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        redSlider = createColorSlider("Red", 255);
        greenSlider = createColorSlider("Green", 255);
        blueSlider = createColorSlider("Blue", 255);

        rgbR = new JTextField("0", 5);
        rgbG = new JTextField("0", 5);
        rgbB = new JTextField("0", 5);

        JPanel rgbPanel = new JPanel(new GridLayout(3, 3));
        rgbPanel.setBorder(BorderFactory.createTitledBorder("RGB"));
        rgbPanel.add(new JLabel("Red:"));
        rgbPanel.add(redSlider);
        rgbPanel.add(rgbR);
        rgbPanel.add(new JLabel("Green:"));
        rgbPanel.add(greenSlider);
        rgbPanel.add(rgbG);
        rgbPanel.add(new JLabel("Blue:"));
        rgbPanel.add(blueSlider);
        rgbPanel.add(rgbB);

        colorPanel = new JPanel();
        colorPanel.setBackground(Color.WHITE);
        colorPanel.setPreferredSize(new Dimension(150, 150));

        JButton colorChooserButton = new JButton("Выбрать цвет");
        colorChooserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color chosenColor = JColorChooser.showDialog(null, "Выбор цвета", colorPanel.getBackground());
                if (chosenColor != null) {
                    redSlider.setValue(chosenColor.getRed());
                    greenSlider.setValue(chosenColor.getGreen());
                    blueSlider.setValue(chosenColor.getBlue());
                    updateFromRGB();  // Обновляем значения после выбора цвета
                }
            }
        });

        cmykCSlider = createColorSlider("C", 100);
        cmykMSlider = createColorSlider("M", 100);
        cmykYSlider = createColorSlider("Y", 100);
        cmykKSlider = createColorSlider("K", 100);

        cmykC = new JTextField("0", 5);
        cmykM = new JTextField("0", 5);
        cmykY = new JTextField("0", 5);
        cmykK = new JTextField("0", 5);

        JPanel cmykPanel = new JPanel(new GridLayout(4, 3));
        cmykPanel.setBorder(BorderFactory.createTitledBorder("CMYK"));
        cmykPanel.add(new JLabel("C:"));
        cmykPanel.add(cmykCSlider);
        cmykPanel.add(cmykC);
        cmykPanel.add(new JLabel("M:"));
        cmykPanel.add(cmykMSlider);
        cmykPanel.add(cmykM);
        cmykPanel.add(new JLabel("Y:"));
        cmykPanel.add(cmykYSlider);
        cmykPanel.add(cmykY);
        cmykPanel.add(new JLabel("K:"));
        cmykPanel.add(cmykKSlider);
        cmykPanel.add(cmykK);

        hsvHSlider = createColorSlider("H", 360);
        hsvSSlider = createColorSlider("S", 100);
        hsvVSlider = createColorSlider("V", 100);

        hsvH = new JTextField("0", 5);
        hsvS = new JTextField("0", 5);
        hsvV = new JTextField("0", 5);

        JPanel hsvPanel = new JPanel(new GridLayout(3, 3));
        hsvPanel.setBorder(BorderFactory.createTitledBorder("HSV"));
        hsvPanel.add(new JLabel("H:"));
        hsvPanel.add(hsvHSlider);
        hsvPanel.add(hsvH);
        hsvPanel.add(new JLabel("S:"));
        hsvPanel.add(hsvSSlider);
        hsvPanel.add(hsvS);
        hsvPanel.add(new JLabel("V:"));
        hsvPanel.add(hsvVSlider);
        hsvPanel.add(hsvV);

        JPanel slidersPanel = new JPanel(new GridLayout(1, 3));
        slidersPanel.add(rgbPanel);
        slidersPanel.add(cmykPanel);
        slidersPanel.add(hsvPanel);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(slidersPanel, BorderLayout.CENTER);
        mainPanel.add(colorPanel, BorderLayout.EAST);
        mainPanel.add(colorChooserButton, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        redSlider.addChangeListener(e -> updateFromRGB());
        greenSlider.addChangeListener(e -> updateFromRGB());
        blueSlider.addChangeListener(e -> updateFromRGB());

        cmykCSlider.addChangeListener(e -> updateFromCMYK());
        cmykMSlider.addChangeListener(e -> updateFromCMYK());
        cmykYSlider.addChangeListener(e -> updateFromCMYK());
        cmykKSlider.addChangeListener(e -> updateFromCMYK());

        hsvHSlider.addChangeListener(e -> updateFromHSV());
        hsvSSlider.addChangeListener(e -> updateFromHSV());
        hsvVSlider.addChangeListener(e -> updateFromHSV());

        rgbR.addActionListener(e -> updateRGBFromText(rgbR, redSlider));
        rgbG.addActionListener(e -> updateRGBFromText(rgbG, greenSlider));
        rgbB.addActionListener(e -> updateRGBFromText(rgbB, blueSlider));

        cmykC.addActionListener(e -> updateCMYKFromText(cmykC, cmykCSlider));
        cmykM.addActionListener(e -> updateCMYKFromText(cmykM, cmykMSlider));
        cmykY.addActionListener(e -> updateCMYKFromText(cmykY, cmykYSlider));
        cmykK.addActionListener(e -> updateCMYKFromText(cmykK, cmykKSlider));

        hsvH.addActionListener(e -> updateHSVFromText(hsvH, hsvHSlider));
        hsvS.addActionListener(e -> updateHSVFromText(hsvS, hsvSSlider));
        hsvV.addActionListener(e -> updateHSVFromText(hsvV, hsvVSlider));

        setVisible(true);
    }

    private JSlider createColorSlider(String name, int max) {
        JSlider slider = new JSlider(0, max);
        slider.setMajorTickSpacing(max / 5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBorder(BorderFactory.createTitledBorder(name));
        return slider;
    }

    private void updateFromRGB() {
        int r = redSlider.getValue();
        int g = greenSlider.getValue();
        int b = blueSlider.getValue();

        rgbR.setText(String.valueOf(r));
        rgbG.setText(String.valueOf(g));
        rgbB.setText(String.valueOf(b));

        colorPanel.setBackground(new Color(r, g, b));

        float[] cmyk = rgbToCmyk(r, g, b);
        cmykC.setText(String.format("%.2f", cmyk[0] * 100));
        cmykM.setText(String.format("%.2f", cmyk[1] * 100));
        cmykY.setText(String.format("%.2f", cmyk[2] * 100));
        cmykK.setText(String.format("%.2f", cmyk[3] * 100));

        float[] hsv = rgbToHsv(r, g, b);
        hsvH.setText(String.format("%.2f", hsv[0]));
        hsvS.setText(String.format("%.2f", hsv[1] * 100));
        hsvV.setText(String.format("%.2f", hsv[2] * 100));
    }

    private void updateFromCMYK() {
        float c = cmykCSlider.getValue() / 100f;
        float m = cmykMSlider.getValue() / 100f;
        float y = cmykYSlider.getValue() / 100f;
        float k = cmykKSlider.getValue() / 100f;

        cmykC.setText(String.format("%.2f", c * 100));
        cmykM.setText(String.format("%.2f", m * 100));
        cmykY.setText(String.format("%.2f", y * 100));
        cmykK.setText(String.format("%.2f", k * 100));

        int[] rgb = cmykToRgb(c, m, y, k);
        redSlider.setValue(rgb[0]);
        greenSlider.setValue(rgb[1]);
        blueSlider.setValue(rgb[2]);

        colorPanel.setBackground(new Color(rgb[0], rgb[1], rgb[2]));

        float[] hsv = rgbToHsv(rgb[0], rgb[1], rgb[2]);
        hsvH.setText(String.format("%.2f", hsv[0]));
        hsvS.setText(String.format("%.2f", hsv[1] * 100));
        hsvV.setText(String.format("%.2f", hsv[2] * 100));
    }

    private void updateFromHSV() {
        float h = hsvHSlider.getValue();
        float s = hsvSSlider.getValue() / 100f;
        float v = hsvVSlider.getValue() / 100f;

        hsvH.setText(String.format("%.2f", h));
        hsvS.setText(String.format("%.2f", s * 100));
        hsvV.setText(String.format("%.2f", v * 100));

        int[] rgb = hsvToRgb(h, s, v);
        redSlider.setValue(rgb[0]);
        greenSlider.setValue(rgb[1]);
        blueSlider.setValue(rgb[2]);

        colorPanel.setBackground(new Color(rgb[0], rgb[1], rgb[2]));

        float[] cmyk = rgbToCmyk(rgb[0], rgb[1], rgb[2]);
        cmykC.setText(String.format("%.2f", cmyk[0] * 100));
        cmykM.setText(String.format("%.2f", cmyk[1] * 100));
        cmykY.setText(String.format("%.2f", cmyk[2] * 100));
        cmykK.setText(String.format("%.2f", cmyk[3] * 100));
    }

    private void updateRGBFromText(JTextField textField, JSlider slider) {
        try {
            int value = Integer.parseInt(textField.getText());
            value = Math.max(0, Math.min(value, 255));
            slider.setValue(value);
        } catch (NumberFormatException e) {
            textField.setText(String.valueOf(slider.getValue()));
        }
    }

    private void updateCMYKFromText(JTextField textField, JSlider slider) {
        try {
            float value = Float.parseFloat(textField.getText());
            value = Math.max(0, Math.min(value, 100));
            slider.setValue((int) value);
        } catch (NumberFormatException e) {
            textField.setText(String.valueOf(slider.getValue()));
        }
    }

    private void updateHSVFromText(JTextField textField, JSlider slider) {
        try {
            float value = Float.parseFloat(textField.getText());
            if (slider == hsvHSlider) {
                value = Math.max(0, Math.min(value, 360));
            } else {
                value = Math.max(0, Math.min(value, 100));
            }
            slider.setValue((int) value);
        } catch (NumberFormatException e) {
            textField.setText(String.valueOf(slider.getValue()));
        }
    }

    public static float[] rgbToCmyk(int r, int g, int b) {
        float c = 1 - (r / 255f);
        float m = 1 - (g / 255f);
        float y = 1 - (b / 255f);
        float k = Math.min(c, Math.min(m, y));

        if (k == 1.0) {
            return new float[]{0, 0, 0, 1};
        }

        return new float[]{
                (c - k) / (1 - k),
                (m - k) / (1 - k),
                (y - k) / (1 - k),
                k
        };
    }

    public static int[] cmykToRgb(float c, float m, float y, float k) {
        int r = (int) (255 * (1 - c) * (1 - k));
        int g = (int) (255 * (1 - m) * (1 - k));
        int b = (int) (255 * (1 - y) * (1 - k));
        return new int[]{r, g, b};
    }

    public static float[] rgbToHsv(int r, int g, int b) {
        float max = Math.max(r, Math.max(g, b)) / 255f;
        float min = Math.min(r, Math.min(g, b)) / 255f;
        float delta = max - min;
        float h = 0;
        float s = max == 0 ? 0 : delta / max;
        float v = max;

        if (delta != 0) {
            if (max == r / 255f) {
                h = 60 * (((g / 255f) - (b / 255f)) / delta % 6);
            } else if (max == g / 255f) {
                h = 60 * (((b / 255f) - (r / 255f)) / delta + 2);
            } else {
                h = 60 * (((r / 255f) - (g / 255f)) / delta + 4);
            }
        }

        if (h < 0) {
            h += 360;
        }

        return new float[]{h, s, v};
    }

    public static int[] hsvToRgb(float h, float s, float v) {
        float c = v * s;
        float x = c * (1 - Math.abs((h / 60) % 2 - 1));
        float m = v - c;

        float rPrime = 0, gPrime = 0, bPrime = 0;

        if (0 <= h && h < 60) {
            rPrime = c;
            gPrime = x;
        } else if (60 <= h && h < 120) {
            rPrime = x;
            gPrime = c;
        } else if (120 <= h && h < 180) {
            gPrime = c;
            bPrime = x;
        } else if (180 <= h && h < 240) {
            gPrime = x;
            bPrime = c;
        } else if (240 <= h && h < 300) {
            rPrime = x;
            bPrime = c;
        } else if (300 <= h && h < 360) {
            rPrime = c;
            bPrime = x;
        }

        int r = (int) ((rPrime + m) * 255);
        int g = (int) ((gPrime + m) * 255);
        int b = (int) ((bPrime + m) * 255);

        return new int[]{r, g, b};
    }

    public static void main(String[] args) {
        new ColorConverterApp();
    }
}