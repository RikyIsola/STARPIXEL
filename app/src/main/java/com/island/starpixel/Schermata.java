package com.island.starpixel;
import android.os.*;
import com.island.*;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.solidi.mobili.ai.intelligenti.*;
import java.util.*;
public class Schermata extends Finestra
{
	Lista<Gioco>giochi;
	private Suono musica;
	private boolean salva;
	private String server,cartella;
	private Gruppo g;
	Schermata(Schermo s,String server,String cartella)
	{
		super(s);
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
		musica=new Suono(schermo(),schermo().blocchi.pace(schermo().blocchi.normale)).start().infinito(true);
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
		boolean esci=false;
		for(Gioco g:giochi)
		{
			g.sempreGrafico();
			if(g.tu!=null)this.g.immagine(g.tu.chunk.immagine);
			for(ChunkLan c:g.chunk)
			{
				for(EntitaLan e:c.entita)
				{
					if(e.b instanceof Intelligente)
					{
						Intelligente i=(Intelligente)e.b;
						if(i.team(schermo().blocchi)!=0)
						{
							esci=true;
							if(!i.boss())
							{
								if(schermo().blocchi.pace(musica.suono(),e.chunk.dimensione)||schermo().blocchi.pace(musica.suono(),schermo().blocchi.normale))
								{
									musica.rilascia();
									musica=new Suono(schermo(),Blocchi.battaglia()).start().infinito(true);
								}
							}
							else
							{
								if(schermo().blocchi.pace(musica.suono(),e.chunk.dimensione)||Blocchi.battaglia(musica.suono()))
								{
									musica.rilascia();
									musica=new Suono(schermo(),Blocchi.boss()).start().infinito(true);
									break;
								}
							}
						}
					}
				}
				if(esci)break;
			}
			int dimensione;
			if(g.tu!=null)dimensione=g.tu.chunk.dimensione;
			else dimensione=schermo().blocchi.normale;
			if(!esci&&(Blocchi.battaglia(musica.suono())||Blocchi.boss(musica.suono())||!schermo().blocchi.pace(musica.suono(),dimensione)))
			{
				musica.rilascia();
				musica=new Suono(schermo(),schermo().blocchi.pace(dimensione)).start().infinito(true);
			}
		}
	}
	public void onStop()
	{
		if(salva)for(Gioco g:giochi)g.ripristino();
		if(schermo().musica!=null)schermo().musica.riprendi();
		musica.rilascia();
		for(Gioco g:giochi)g.onStop();
		for(Blocco b:schermo().blocchi.blocchi)
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
