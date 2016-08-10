package com.example.pc.buquan3;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;


/**
 * Created by PC on 2016/8/9.
 */
public class Competion {
    public String str0[]={"int","double","void","char","float","double","short","long","signed","unsigned","struct","union","enum","typedef","sizeof","auto","static","register","extern","const",
            "volatile","return","continue","break","goto","if","else","switch","case","default","for","do","while","String","string","include","public","private","protected"};
    String strDef;
    private ArrayList mResultsValues = new ArrayList();
    public int startPositon,start;
    int endPositon;
    boolean aBoolean,addBoolean=false;
    public String string,string2;
    private EditText editText;
    public Competion(EditText editText){
        this.editText = editText;
        for(int i=0;i<str0.length;i++)mResultsValues.add(str0[i]);
    }
    public  void setcompleting(){
        CompletingWatcher watcher = new CompletingWatcher();
        editText.addTextChangedListener(watcher);
    }
    public class CompletingWatcher implements TextWatcher {
        String strNow = null;
        String strOld = null;
        int strLength = -1;
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            return;
        }
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(addBoolean)return;
            Log.d(startPositon +"::",""+editText.getSelectionStart());
            aBoolean = startPositon >= editText.getSelectionStart();
            if (aBoolean) {
                startPositon=editText.getSelectionStart();
                string = s.toString().substring(0, editText.getSelectionStart());
                strNow = null;
                strOld = null;
                return;
            }
            if (s.toString().charAt(editText.getSelectionStart() - 1) < 'A'
                    || s.toString().charAt(editText.getSelectionStart() - 1) > 'Z'
                    && s.toString().charAt(editText.getSelectionStart() - 1) < 'a'
                    || s.toString().charAt(editText.getSelectionStart() - 1) > 'z') {
                strNow = null;
                string = s.toString().substring(0, editText.getSelectionStart());
            } else if (strNow == null)
                strNow = s.toString().charAt(start) + "";
            else strNow += s.toString().charAt(start);
            return;
        }


        public void afterTextChanged(Editable s) {
            if (aBoolean){aBoolean=false;return;}
            int i;
            if(addBoolean){
                editText.setSelection(start,endPositon);
                addBoolean=false;
                return;
            }
            if(aBoolean)return;
            startPositon = editText.getSelectionStart();
            for (i = 0; i < mResultsValues.size(); i++) {
                strDef = mResultsValues.get(i).toString();
                if(strNow!=null){
                    if (strDef.startsWith(strNow) && !strDef.equals(strNow)) {
                        if(strNow.length()>1){
                            start++;
                            addBoolean=true;
                            if(string!=null){
                                endPositon = strDef.length()+string.length();
                                s.insert(string.length()+strNow.length(),strDef.substring(strNow.length()));
                            }
                            else {
                                endPositon=strDef.length();
                                s.insert(strNow.length(),strDef.substring(strNow.length()));
                            }

                            return;
                        }

                        if(string!=null)
                            endPositon = strDef.length()+string.length();
                        else endPositon=strDef.length();
                        if(string!=null){
                            addBoolean=true;
                            start=string.length()+strNow.length();
                            endPositon=string.length()+strDef.length();
                            s.insert(string.length()+1,strDef.substring(1));
                        }
                        else {
                            addBoolean=true;
                            start=strNow.length();
                            endPositon=strDef.length();
                            s.insert(1,strDef.substring(1));
                        }
                        break;
                    }
                }
            }
            if(i==mResultsValues.size()){strNow=null;string = s.toString().substring(0, editText.getSelectionStart());}
            return;
        }
    }
}

