package com.island.starpixel;
import android.os.*;
import com.island.*;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.solidi.mobili.ai.intelligenti.*;
import java.util.*;
public class Schermata extends Finestra
{
	Lista<Gioco>giochi;
	private boolean salva;
	private String server,cartella;
	private Gruppo g;
	private Blocchi blocchi;
	Schermata(Schermo s,String server,String cartella)
	{
		super(s);
		blocchi=schermo().blocchi;
		final int n=1;
		this.server=server;
		this.cartella=cartella;
		schermo().musica.pausa();
		if(server.equals(Lan.local()))
		{
			g=new Gruppo(this,(n>1?2:1),(n+1)/2);
			giochi=new Lista<Gioco>(n);
			for(int a=0;a<n;a++)giochi.add(new Gioco(this,g,a%2,a/2,a%2+1,a/2+1,server,cartella,a==0,a==0));
		}
		else
		{
			g=new Gruppo(this,1,1);
			giochi=new Lista<Gioco>(1);
			giochi.add(new Gioco(this,g,0,0,1,1,server,cartella,false,true));
		}
		g.aggiorna();
	}
	public void sempre()
	{
		if(giochi!=null)for(Gioco g:giochi)
		{
			if(g!=null)g.sempre();
		}
	}
	public void sempreGrafico()
	{
		schermo().inizioDebug(0);
		for(Gioco g:giochi)
		{
			//g.sempreGrafico();
			g.aggiorna();
			if(g.tu!=null)this.g.immagine(g.tu.chunk.immagine);
		}
		schermo().fineDebug(0);
	}
	public void onStop()
	{
		if(salva)for(Gioco g:giochi)g.ripristino();
		if(schermo().musica!=null)schermo().musica.riprendi();
		for(Gioco g:giochi)g.onStop();
		for(Blocco b:blocchi.blocchi)
		{
			schermo().cancella(b.immagine());
			schermo().cancella(b.id+1);
		}
		super.onStop();
	}
	public void salva(Bundle b)
	{
		b.putBoolean("SCHERMATA",true);
		b.putString("SCHERMATA SERVER",server);
		b.putString("SCHERMATA CARTELLA",cartella);
		for(Gioco g:giochi)g.salva(b);
		salva=true;
	}
	public void leggi(Bundle b)
	{
		for(Gioco g:giochi)g.leggi(b);
	}
	public void pausa()
	{
		for(Gioco g:giochi)g.pausa();
	}
	public void riprendi()
	{
		for(Gioco g:giochi)g.riprendi();
		salva=false;
	}
	public MainActivity schermo()
	{
		return(MainActivity)super.schermo();
	}
	public void onBackPressed()
	{
		if(giochi.get(0).finito)super.onBackPressed();
	}
}
