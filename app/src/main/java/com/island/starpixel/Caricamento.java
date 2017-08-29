package com.island.starpixel;
import com.island.*;
import android.graphics.*;
public class Caricamento extends Finestra
{
	private Barra caricamento;
	private Testo percentuale;
	private String percento="%";
	private double fatto;
	Caricamento(Schermo s,String testo)
	{
		super(s);
		Gruppo g=new Gruppo(this,5,5);
		new Testo(g,0,1,5,2,testo);
		caricamento=new Barra(g,0.1,3,4.9,4,Color.BLUE,Color.BLACK);
		percentuale=new Testo(g,0,3,5,4,"0"+percento);
		g.aggiorna();
	}
	void carica(double caricato)
	{
		fatto+=caricato;
		if(fatto>=100)cancel();
		else
		{
			caricamento.caricamento(fatto);
			percentuale.scrivi((int)fatto+percento);
		}
	}
	public void onBackPressed(){}
}
