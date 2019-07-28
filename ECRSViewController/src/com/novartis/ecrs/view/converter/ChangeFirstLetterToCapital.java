package com.novartis.ecrs.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.apache.myfaces.trinidad.context.RequestContext;

public class ChangeFirstLetterToCapital implements Converter{
    public ChangeFirstLetterToCapital() {
        super();
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uIComponent, String string) {
        if (string != null) {
            // trim the entered value
                   RequestContext adfContext = RequestContext.getCurrentInstance();
                   adfContext.addPartialTarget(uIComponent);
                   String firstLetter = Character.toString(string.charAt(0)).toUpperCase();
                   if(string.length() > 1){
                   String remainingLetters = string.substring(1, string.length()).toLowerCase();
                   return firstLetter + remainingLetters;
                   }else{
                       return firstLetter;
                   }
               }
        return string;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            RequestContext adfContext = RequestContext.getCurrentInstance();
            adfContext.addPartialTarget(uIComponent);
            if(object != null && object.toString().length() > 0){
                String firstLetter = Character.toString(object.toString().charAt(0)).toUpperCase();
                if(object.toString().length() > 1){
                String remainingLetters = object.toString().substring(1, object.toString().length()).toLowerCase();
                return firstLetter + remainingLetters;
                }else{
                    return firstLetter;
                }
            }
            }
               return null;
    }
}
