package fr.itescia.blablasam.blablasam;


        import android.app.Fragment;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.Toast;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private Button btnConnexion;
    private Button btnInscription;

    public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        btnConnexion = (Button)rootView.findViewById(R.id.btnConnexion);
        btnConnexion.setOnClickListener(this);

        btnInscription = (Button)rootView.findViewById(R.id.btnInscription);
        btnInscription.setOnClickListener(this);




        return rootView;
    }

    @Override
    public void onClick(View v) {
try {
    switch (v.getId()) {
        case R.id.btnConnexion:

            Toast.makeText(v.getContext(), "Connexion", Toast.LENGTH_SHORT).show();

            break;

        case R.id.btnInscription:
            Toast.makeText(getActivity(), "Inscription", Toast.LENGTH_SHORT).show();

            break;

    }
}
catch (Exception ex)
{
    System.out.println(ex.getMessage());
}


    }
}
