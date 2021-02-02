package vbencek.mvc;

public class IspisController {

    private IspisModel model;
    private View view;

    public IspisController(IspisModel model, View view) {
        this.model = model;
        this.view = view;
    }

    public void setIspisNacinRada(NacinRada nacinRada) {
        model.setNacinRada(nacinRada);
    }

    public NacinRada getIspisNacinRada() {
        return model.getNacinRada();
    }
    
    public void updateView(){                
      view.ispisi(model.getNacinRada());
   }
}
