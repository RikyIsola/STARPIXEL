package com.island.starpixel;
import android.graphics.*;
import android.view.*;
import com.island.*;
import com.island.starpixel.blocchi.*;
import java.io.*;
import android.os.*;
public class Aiuto extends Finestra
{
	private Gruppo g;
	Aiuto(Schermo schermo)
	{
		super(schermo);
		g=new Gruppo(this,1,1);
		new SuperTesto(g,0,0,1,2.5,schermo().lingua.descrizione,Color.WHITE,Color.BLUE).carattere(0.023).allineamento(Paint.Align.LEFT).aggiorna();
		for(int a=0;a<schermo().blocchi.blocchi.length;a++)
		{
			new Bottone(g,0.1+a%4*0.2,2.5+a/4*0.25,0.3+a%4*0.2,a/4*0.25+2.7).immagine(schermo().blocchi.blocchi[a].immagine()).setOnClickListener(click(schermo().blocchi.blocchi[a]));
			new Testo(g,0.1+a%4*0.2,2.7+a/4*0.25,0.3+a%4*0.2,a/4*0.25+2.75,schermo().lingua.nome(schermo().blocchi.blocchi[a])).larghezzaX(0.5);
		}
		g.aggiorna();
	}
	public MainActivity schermo()
	{
		return(MainActivity)super.schermo();
	}
	public void sempreGrafico()
	{
		if(libero())g.sempre(0.001);
	}
	public void salva(Bundle b)
	{
		b.putBoolean("AIUTO",true);
		b.putDouble("AIUTO TRANS",g.transY());
	}
	public void leggi(Bundle b)
	{
		g.trans(0,b.getDouble("AIUTO TRANS"));
		g.aggiorna();
	}
	public void onStop()
	{
		for(Blocco b:schermo().blocchi.blocchi)schermo().cancella(b.immagine());
		super.onStop();
	}
	private View.OnClickListener click(final Blocco b)
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				new Descrizione(schermo(),b);
			}
		};
	}
}
