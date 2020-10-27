import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DictionaryApplication extends DictionaryManagement {
    private final Translator translator = new Translator();
    private String fileNameDictionary = "E_V.txt";
    private String title = "E_V Dictionary";
    private String selectedTarget = "";                                                 //từ search được
    private String popupTarget = "";                                                    //từ lấy từ khi nhấp chuột phải
    private boolean checkOpenMyWords = false;                                           //kiểm tra có mở my Words
    protected List<String> resultList = new ArrayList<>();                              // danh sách từ đã được search

    private final JFrame mainFrame = new JFrame("Dictionary");
    private final JFrame apiFrame = new JFrame("Google Translate");


    private final ImageIcon E_VIcon = new ImageIcon("E_V.png");
    private final ImageIcon V_EIcon = new ImageIcon("V_E.png");
    private final ImageIcon openGoogleApi = new ImageIcon("OpenGoogleTranslate.png");
    private final ImageIcon google = new ImageIcon("GoogleTranslate.png");
    private final ImageIcon searchAPIIcon = new ImageIcon("SearchIcon.png");
    private final ImageIcon soundOnIcon = new ImageIcon("SoundOnIcon.png");
    private final ImageIcon saveOff = new ImageIcon("StarEmpty.png");
    private final ImageIcon saveOn = new ImageIcon("StarSaved.png");
    private final ImageIcon returnMainIcon = new ImageIcon("ReturnMain.png");
    private final ImageIcon PrevIcon = new ImageIcon("PrevIcon.png");
    private final ImageIcon NextIcon = new ImageIcon("NextIcon.png");
    private final ImageIcon AddIcon = new ImageIcon("AddIcon.png");
    private final ImageIcon FixIcon = new ImageIcon("FixIcon.png");
    private final ImageIcon RemoveIcon = new ImageIcon("RemoveIcon.png");
    private final ImageIcon OpenMyWordsIcon = new ImageIcon("OpenMyWords.png");
    private final ImageIcon OutMyWordsIcon = new ImageIcon("OutMyWords.png");
    private final ImageIcon ImportFromFile = new ImageIcon("ImportFromFile.png");
    private final ImageIcon ExportToFile = new ImageIcon("ExportToFile.png");
    private final ImageIcon ExitIcon = new ImageIcon("ExitIcon.png");


    private final JLabel titleLabel = new JLabel(title,JLabel.CENTER);             // nhãn trên cùng
    private final JLabel searchTitle = new JLabel("Search here: ", JLabel.CENTER);        // nhãn cạnh ô search
    private final JLabel targetLabel = new JLabel("",JLabel.CENTER);                      // in ra từ search được
    private final JLabel googleLabel = new JLabel(google,JLabel.CENTER);                       // nhãn google translate

    private final JButton searchButton = new JButton("Search");                           //nút search
    private final JButton soundOn = new JButton(soundOnIcon);                                  //nút phát âm
    private final JButton saveOn_Off = new JButton();                                          //nút thêm hoặc bỏ thêm vào my Words
    private final JButton returnMain = new JButton(returnMainIcon);                            //nút trở lại từ điển chính
    private final JButton PrevResult = new JButton(PrevIcon);                                  //nút trở lại từ search được trước đó
    private final JButton NextResult = new JButton(NextIcon);                                  //nút next từ search được
    private final JButton searchAPIButton = new JButton(searchAPIIcon);                              //nút search cua api

    //các thành phần chính
    private final JTextField searchText = new JTextField();                                    //ô nhập từ
    private final JList<String> selectionList = new JList<>();                                 //danh sách từ search
    private final JScrollPane listSP = new JScrollPane(selectionList);                         //thanh cuốn cho list
    private final JTextPane resultTextPane = new JTextPane();                                  //TextPane ghi ket qua translate
    private final JScrollPane resultScroll = new JScrollPane(resultTextPane);                  //thanh cuốn cho TextPane
    private final JTextArea searchAPI = new JTextArea();                                        // ô nhập từ search api
    private final JScrollPane searchAPISP = new JScrollPane(searchAPI);
    private final JTextArea translateAPI = new JTextArea();                                    //Text ghi kết quả api
    private final JScrollPane translateAPISP = new JScrollPane(translateAPI);

    //Menu
    private final JMenuBar menuBar = new JMenuBar();                                           //tool bar
    private final JMenuBar apiMenuBar = new JMenuBar();
    private final JPopupMenu popupMenu = new JPopupMenu();                                     //menu cho nút chuột phải
    private final JMenu fileMenu = new JMenu("File");
    private final JMenu editMenu = new JMenu("Edit");
    private final JMenu apiMenu = new JMenu("File");

    //menuItem chính
    private final JMenuItem AddMenu = new JMenuItem("Add",AddIcon);
    private final JMenuItem FixMenu = new JMenuItem("Fix",FixIcon);
    private final JMenuItem RemoveMenu = new JMenuItem("Remove",RemoveIcon);
    private final JMenuItem convertItem = new JMenuItem("V_E Dictionary",V_EIcon);
    private final JMenuItem Open_OutMyWords = new JMenuItem("Open My Words",OpenMyWordsIcon);
    private final JMenuItem importFile = new JMenuItem("ImportFromFile",ImportFromFile);
    private final JMenuItem exportFile = new JMenuItem("ExportToFile",ExportToFile);
    private final JMenuItem exitMenu = new JMenuItem("Exit",ExitIcon);
    private final JMenuItem openApi = new JMenuItem("Google Translate",openGoogleApi);
    private final JMenuItem exitJavaAPI = new JMenuItem("Exit",ExitIcon);
    // menuItem cho nút chuột phải
    private final JMenuItem FixPopup = new JMenuItem("Fix");
    private final JMenuItem RemovePopup = new JMenuItem("Remove");
    private final JMenuItem savePopup = new JMenuItem("Save");
    private final JMenuItem SoundPopup = new JMenuItem("Pronounce",soundOnIcon);

    private final JMenuItem closeAPi = new JMenuItem("Close APi",OutMyWordsIcon);
    //hàm mở cửa sổ google translate
    private void OpenGoogleTranslateAPI() {
        apiFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        apiFrame.setLocationByPlatform(true);

        apiFrame.setSize(400,500);

        googleLabel.setBounds(50,0,300,60);
        searchAPI.setLineWrap(true);
        searchAPI.setWrapStyleWord(true);


        searchAPI.setFont(new Font("Verdana",Font.PLAIN,16));
        searchAPISP.setBounds(32,70, 340, 140);

        searchAPIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if (translator.checkInternet()) {
                   try {
                       translateAPI.setText(translator.translateAPI(searchAPI.getText()));
                   } catch (IOException ioException) {
                       translateAPI.setText("Word not found!!");
                       ioException.printStackTrace();
                   }
               }
               else {
                   JOptionPane.showMessageDialog(apiFrame,"No Internet !", "message",
                           JOptionPane.WARNING_MESSAGE);
               }
            }
        });
        searchAPIButton.setBounds(0,70,32,32);

        translateAPI.setWrapStyleWord(true);
        translateAPI.setLineWrap(true);
        translateAPI.setFont(new Font("Verdana",Font.PLAIN,16));
        translateAPI.setEditable(false);
        translateAPISP.setBounds(32,220,340,210);



        closeAPi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(true);
                apiFrame.setVisible(false);
                apiFrame.dispose();
            }
        });
        apiMenu.add(closeAPi);

        exitJavaAPI.addActionListener(e -> System.exit(0));
        apiMenu.add(exitJavaAPI);
        apiMenuBar.add(apiMenu);

        apiFrame.setJMenuBar(apiMenuBar);
        apiFrame.add(googleLabel);
        apiFrame.add(searchAPISP);
        apiFrame.add(searchAPIButton);
        apiFrame.add(translateAPISP);

        apiFrame.setLayout(null);
        apiFrame.setResizable(false);
        apiFrame.setVisible(true);
        mainFrame.setVisible(false);
    }


    //hàm viết translate
    private void translate() {
        resultTextPane.setText("");
        String explain = dictionaryLookup(selectedTarget);
        if (explain.equals("Word not found !!!")) {
            resultTextPane.setText(explain);
            saveOn_Off.setVisible(false);
        }
        else {
            targetLabel.setText(selectedTarget);
            if (!resultList.contains(selectedTarget)) resultList.add(selectedTarget);
            if (resultList.size() >= 2) {
                if (resultList.indexOf(selectedTarget) == 0) {
                    PrevResult.setVisible(false);
                    NextResult.setVisible(true);
                } else if (resultList.indexOf(selectedTarget) == resultList.size() - 1) {
                    PrevResult.setVisible(true);
                    NextResult.setVisible(false);
                } else {
                    PrevResult.setVisible(true);
                    NextResult.setVisible(true);
                }
            }
            saveOn_Off.setVisible(true);
            soundOn.setVisible(true);
            if (myE_VWords.contains(selectedTarget) || myV_EWords.contains(selectedTarget)) saveOn_Off.setIcon(saveOn);
            else saveOn_Off.setIcon(saveOff);

            resultTextPane.setContentType("text/html");
            resultTextPane.setText(dictionaryLookup(selectedTarget));
        }
    }
    public DictionaryApplication() throws Exception {
        prepareGUI();
    }
    private void prepareGUI() throws Exception{
        //insertFromFile("dictionaries.txt");
        insertFromHTMLFile(fileNameDictionary);
        insertMyWords("MyV_EWords.txt");
        insertMyWords("MyE_VWords.txt");
        int frameWidth = 800;
        int frameHeight = 600;
        mainFrame.setSize(frameWidth, frameHeight);

        //label
        titleLabel.setFont(new Font("Verdana",Font.PLAIN,36));
        titleLabel.setBounds(250,0,300,50);

        searchTitle.setBounds(0,100,80,30);

        targetLabel.setBounds(490,62,256,36);
        targetLabel.setFont(new Font("Verdana", Font.BOLD,14));

        //chuẩn bị cho button
        VoiceManager vm = VoiceManager.getInstance();
        Voice voice = vm.getVoice("kevin16");
        voice.allocate();
        Border border = new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {

            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(0, 0, 0, 0);
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        };

        //nút search
        searchButton.setBounds(305,100,75,30);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedTarget = searchText.getText();
                translate();
            }
        });

        //nút phát âm
        soundOn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                voice.speak(selectedTarget);
            }
        });
        soundOn.setBorder(border);
        soundOn.setBounds(430,76,24,24);
        soundOn.setVisible(false);
        //nút thêm hoặc bỏ thêm My Words
        saveOn_Off.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileNameDictionary.equals("E_V.txt")) {
                    if (myE_VWords.contains(selectedTarget)) {
                        myE_VWords.remove(selectedTarget);
                        saveOn_Off.setIcon(saveOff);
                    } else {
                        myE_VWords.add(selectedTarget);
                        saveOn_Off.setIcon(saveOn);
                    }
                }
                else {
                    if (myV_EWords.contains(selectedTarget)) {
                        myV_EWords.remove(selectedTarget);
                        saveOn_Off.setIcon(saveOff);
                    } else {
                        myV_EWords.add(selectedTarget);
                        saveOn_Off.setIcon(saveOn);
                    }
                }


            }
        });
        saveOn_Off.setBorder(border);
        saveOn_Off.setBounds(400, 76, 24, 24);
        saveOn_Off.setVisible(false);

        //nút trở lại từ điển chính
        returnMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchText.setText("");
                targetLabel.setText("");
                selectionList.setModel(dictionarySearch(""));
                resultTextPane.setText("");
                titleLabel.setText(title);
                Open_OutMyWords.setText("Open My Words");
                Open_OutMyWords.setIcon(OpenMyWordsIcon);
                checkOpenMyWords = false;
                NextResult.setVisible(false);
                PrevResult.setVisible(false);
                saveOn_Off.setVisible(false);
                soundOn.setVisible(false);
                returnMain.setVisible(false);
            }
        });
        returnMain.setBorder(border);
        returnMain.setBounds(25,25,32,32);
        returnMain.setVisible(false);

        //nút trở lại từ vừa search
        PrevResult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedTarget = resultList.get(resultList.indexOf(selectedTarget) - 1);
                translate();
            }
        });
        PrevResult.setBorder(border);
        PrevResult.setBounds(461, 70, 24, 24);
        PrevResult.setVisible(false);

        //nút next từ search
        NextResult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedTarget = resultList.get(resultList.indexOf(selectedTarget) + 1);
                translate();
            }
        });
        NextResult.setBorder(border);
        NextResult.setBounds(746, 70, 24, 24);
        NextResult.setVisible(false);

        //TextField cho search
        searchText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    selectedTarget= searchText.getText();
                    translate();
                } else {
                    if (!checkOpenMyWords)
                        selectionList.setModel(dictionarySearch(searchText.getText()));
                    else selectionList.setModel(dictionaryMyWords(searchText.getText(),fileNameDictionary));
                }

            }

        });
        searchText.setFont(new Font("Verdana",Font.PLAIN,18));
        searchText.setBounds(80,100,225,30);

        //List từ search
        selectionList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (SwingUtilities.isRightMouseButton(e)) {
                    selectionList.setSelectedIndex(selectionList.locationToIndex(e.getPoint()));
                    popupTarget = selectionList.getSelectedValue();
                    if (myE_VWords.contains(popupTarget) || myV_EWords.contains(popupTarget)) {
                        savePopup.setIcon(saveOn);
                        savePopup.setText("Remove from myWords");
                    } else {
                        savePopup.setIcon(saveOff);
                        savePopup.setText("Save");
                    }
                    popupMenu.show(selectionList, e.getX(), e.getY());
                }

                if (SwingUtilities.isLeftMouseButton(e)) {
                    selectedTarget = selectionList.getSelectedValue();
                    translate();
                }

            }
        });
        selectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionList.setForeground(Color.BLACK);
        selectionList.setModel(dictionarySearch(""));
        listSP.setBounds(0,140,380,390);

        //TextPane cho Translate
        resultTextPane.setEditable(false);
        resultScroll.setBounds(400,100,370,430);


        //AddItem
        AddMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField English = new JTextField(25);
                JTextArea Vietnamese = new JTextArea(10,25);
                Vietnamese.setLineWrap(true);
                Object[] msg = {"English:", English, "Vietnamese :",Vietnamese};

                int result = JOptionPane.showConfirmDialog(mainFrame, msg, "Add Word",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.YES_OPTION) {
                    if (!dictionaryLookup(English.getText()).equals("Word not found !!!")) {
                        JOptionPane.showMessageDialog(mainFrame, "The dictionaries already have this word!"
                                , "Message", JOptionPane.WARNING_MESSAGE);
                    } else {
                        if (Vietnamese.getText().length() == 0) {
                            JOptionPane.showMessageDialog(mainFrame, "Please write Vietnamese explain word!"
                                    , "Message", JOptionPane.WARNING_MESSAGE);
                        }
                        else {
                            //System.out.println(English.getText() + " " + Vietnamese.getText());
                            String explain ="<html><i>" + English.getText() + "</i><br/><ul><li><font color='#cc0000'><b>"
                           + Vietnamese.getText() + " </b></font></li></ul></html>";
                            addWord(English.getText(), explain);
                            File file = new File(fileNameDictionary);
                            file.delete();
                            try {
                                dictionaryExportToFile(fileNameDictionary);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            JOptionPane.showMessageDialog(mainFrame, "Add is completed!"
                                    , "message", JOptionPane.INFORMATION_MESSAGE);
                            System.out.println("Add is used");
                        }

                    }
                }
                else {
                    System.out.println("Add is not used");
                }
            }
        });
        AddMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));

        //FixItem
        FixMenu.addActionListener(e -> {
            JTextField English = new JTextField(25);

            Object[] msg = {"English Word to Fix:", English};
            int result = JOptionPane.showConfirmDialog(mainFrame, msg, "Fix Word",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.YES_OPTION) {
                if (dictionaryLookup(English.getText()).equals("Word not found !!!")) {
                    JOptionPane.showMessageDialog(mainFrame, "Can not look up this word!"
                            , "Message", JOptionPane.WARNING_MESSAGE);
                } else {
                    String word = English.getText();
                    System.out.println("Word is Fix: " + word);
                    JTextArea Vietnamese = new JTextArea(10,25);
                    Vietnamese.setLineWrap(true);
                    Vietnamese.setText(dictionaryLookup(word));
                    Object[] msg1 = {"English Word :  " + word,
                            "Vietnamese :", Vietnamese};
                    int YN = JOptionPane.showConfirmDialog(mainFrame, msg1, "Fix Word",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (YN == JOptionPane.YES_OPTION) {
                        modifyWord(word, Vietnamese.getText()+"\n");
                        File file = new File(fileNameDictionary);
                        file.delete();
                        try {
                            dictionaryExportToFile(fileNameDictionary);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        JOptionPane.showMessageDialog(mainFrame, "This word has been modified!"
                                , "message", JOptionPane.INFORMATION_MESSAGE);

                    }
                }
            }
        });
        FixMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));

        //RemoveItem
        RemoveMenu.addActionListener(e -> {
            JTextField English = new JTextField(25);

            Object[] msg ={"English Word to Remove:", English};
            int result = JOptionPane.showConfirmDialog(mainFrame, msg, "Remove Word",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.YES_OPTION) {
                if (dictionaryLookup(English.getText()).equals("Word not found !!!")) {
                    JOptionPane.showMessageDialog(mainFrame, "Can not look up this word!"
                            , "Message", JOptionPane.WARNING_MESSAGE);
                } else {
                    String word = English.getText();
                    //System.out.println("Word is Removed: " + word);
                    JTextArea Vietnamese = new JTextArea(10,25);
                    Vietnamese.setLineWrap(true);
                    Vietnamese.setEditable(false);
                    Vietnamese.setText(dictionaryLookup(word));
                    Object[] msg1 = {"English Word : " + word,
                            "Vietnamese : ", Vietnamese, "Do you want to remove this word?"};
                    int YN = JOptionPane.showConfirmDialog(mainFrame, msg1, "Question to Remove",
                            JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (YN == JOptionPane.YES_OPTION) {
                        removeWord(word);
                        File file = new File(fileNameDictionary);
                        file.delete();
                        try {
                            dictionaryExportToFile(fileNameDictionary);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        JOptionPane.showMessageDialog(mainFrame, "This word has been removed from " + fileNameDictionary
                                , "message", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        RemoveMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));

        //Open API Google Translate
        openApi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (translator.checkInternet()) {
                    OpenGoogleTranslateAPI();
                }
                else {
                    JOptionPane.showMessageDialog(mainFrame,"No Internet !", "message",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        openApi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,InputEvent.CTRL_MASK));

        //convert E_V V_E
        convertItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchText.setText("");
                targetLabel.setText("");
                saveOn_Off.setVisible(false);
                soundOn.setVisible(false);
                NextResult.setVisible(false);
                PrevResult.setVisible(false);
                resultList.clear();
                if (fileNameDictionary.equals("V_E.txt")) {
                    fileNameDictionary = "E_V.txt";
                    resultTextPane.setText("");
                    title = "E_V Dictionary";
                    convertItem.setText("V_E Dictionary");
                    convertItem.setIcon(V_EIcon);
                }
                else {
                    resultTextPane.setText("");
                    title = "V_E Dictionary";
                    convertItem.setText("E_V Dictionary");
                    convertItem.setIcon(E_VIcon);
                    fileNameDictionary = "V_E.txt";
                }

                try {
                    insertFromHTMLFile(fileNameDictionary);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                selectionList.setModel(dictionarySearch(""));
                titleLabel.setText(title);
            }
        });
        convertItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_MASK));
        //MyWordsItem
        Open_OutMyWords.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchText.setText("");
                targetLabel.setText("");
                saveOn_Off.setVisible(false);
                soundOn.setVisible(false);
                NextResult.setVisible(false);
                PrevResult.setVisible(false);
                resultTextPane.setText("");
                if (!checkOpenMyWords) {
                    returnMain.setVisible(true);
                    selectionList.setModel(dictionaryMyWords("",fileNameDictionary));
                    titleLabel.setText("My Words");
                    Open_OutMyWords.setText("Out of My Words");
                    Open_OutMyWords.setIcon(OutMyWordsIcon);
                    checkOpenMyWords = true;
                }
                else {
                    returnMain.setVisible(false);
                    selectionList.setModel(dictionarySearch(""));
                    titleLabel.setText(title);
                    Open_OutMyWords.setText("Open My Words");
                    Open_OutMyWords.setIcon(OpenMyWordsIcon);
                    checkOpenMyWords = false;
                }
            }
        });
        Open_OutMyWords.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));

        // nhập từ từ file dictionaries.txt
        importFile.addActionListener(e -> {
            try {
                insertFromFile("dictionaries.txt");
                JOptionPane.showMessageDialog(mainFrame, "Insert from dictionaries.txt completely!"
                        , "message", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        importFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));

        //xuất từ điển ra file, hoặc lưu từ mình thích ra file myWords.txt
        exportFile.addActionListener(e -> {

            try {
                if (checkOpenMyWords) exportToMyFile();
                else {
                    JTextField createFile = new JTextField(22);
                    Object[] msg = {"Create new file (Ex: new, newFile, v.v...) :", createFile, ".txt"};
                    int create = JOptionPane.showConfirmDialog(mainFrame, msg, "Create file",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (create == JOptionPane.OK_OPTION) {
                        dictionaryExportToFile(createFile.getText() + ".txt");
                        JOptionPane.showMessageDialog(mainFrame, "Export dictionary to " + createFile.getText() + ".txt completely!",
                                "message", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        exportFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));

        //Exit
        exitMenu.addActionListener(e ->{
            try {
                exportToMyFile();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            System.exit(0);
        });
        exitMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));

        //add item vao menu
        editMenu.add(AddMenu);
        editMenu.add(FixMenu);
        editMenu.add(RemoveMenu);

        fileMenu.add(openApi);
        fileMenu.add(convertItem);
        fileMenu.add(Open_OutMyWords);
        fileMenu.add(importFile);
        fileMenu.add(exportFile);
        fileMenu.add(exitMenu);

        fileMenu.addSeparator();
        editMenu.addSeparator();

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        //menu cho nhấp chuột phải ở list
        //save
        savePopup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileNameDictionary.equals("E_V.txt")) {
                    if (myE_VWords.contains(popupTarget)) myE_VWords.remove(popupTarget);
                    else myE_VWords.add(popupTarget);
                }
                else {
                    if (myV_EWords.contains(popupTarget)) myV_EWords.remove(popupTarget);
                    else myV_EWords.add(popupTarget);
                }
            }
        });

        //phát âm
        SoundPopup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voice.speak(popupTarget);
            }
        });

        //sửa từ
        FixPopup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea Vietnamese = new JTextArea(10,25);
                Vietnamese.setLineWrap(true);
                Vietnamese.setText(dictionaryLookup(popupTarget));
                Object[] msg1 = {"English Word :  " + popupTarget,
                        "Vietnamese :", Vietnamese};
                int YN = JOptionPane.showConfirmDialog(mainFrame, msg1, "Fix Word",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (YN == JOptionPane.YES_OPTION) {
                    modifyWord(popupTarget, Vietnamese.getText()+"\n");
                    File file = new File(fileNameDictionary);
                    file.delete();
                    try {
                        dictionaryExportToFile(fileNameDictionary);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(mainFrame, "This word has been modified!"
                            , "message", JOptionPane.INFORMATION_MESSAGE);

                }
            }
        });

        //xóa từ
        RemovePopup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea Vietnamese = new JTextArea(10,25);
                Vietnamese.setLineWrap(true);
                Vietnamese.setEditable(false);
                Vietnamese.setText(dictionaryLookup(popupTarget));
                Object[] msg1 = {"English Word : " + popupTarget,
                        "Vietnamese : ", Vietnamese, "Do you want to remove this word?"};
                int YN = JOptionPane.showConfirmDialog(mainFrame, msg1, "Question to Remove",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (YN == JOptionPane.YES_OPTION) {
                    removeWord(popupTarget);
                    File file = new File(fileNameDictionary);
                    file.delete();
                    try {
                        dictionaryExportToFile(fileNameDictionary);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(mainFrame, "This word has been removed from" + fileNameDictionary
                            , "message", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //thêm item vào popupMenu
        popupMenu.add(savePopup);
        popupMenu.add(SoundPopup);
        popupMenu.add(FixPopup);
        popupMenu.add(RemovePopup);

        //thêm tất cả vào frame
        mainFrame.add(searchTitle);
        mainFrame.add(targetLabel);
        mainFrame.add(titleLabel);

        mainFrame.add(searchButton);
        mainFrame.add(soundOn);
        mainFrame.add(saveOn_Off);
        mainFrame.add(PrevResult);
        mainFrame.add(NextResult);
        mainFrame.add(returnMain);

        mainFrame.add(searchText);
        mainFrame.add(listSP);
        mainFrame.add(resultScroll);

        mainFrame.setJMenuBar(menuBar);

        mainFrame.setResizable(false);
        mainFrame.setLayout(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        new DictionaryApplication();
    }
}