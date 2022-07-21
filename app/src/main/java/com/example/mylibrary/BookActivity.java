package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    public static final String BOOK_ID_KEY = "bookID";

    private TextView txtBookName, txtAuthor, txtPages, txtDescripition;
    private Button btnAddWishList,btnAddToAlreadyRead, btnAddToCurrentlyReading, btnAddToFavorite;
    private ImageView bookImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        txtAuthor = findViewById(R.id.txtAuthorName);
        txtBookName = findViewById(R.id.txtBookName);
        txtPages = findViewById(R.id.txtPages);
        txtDescripition = findViewById(R.id.txtDescripition);

        btnAddToAlreadyRead = findViewById(R.id.btnAddAlreadyRead);
        btnAddToCurrentlyReading = findViewById(R.id.btnAddToCurrentlyReading);
        btnAddToFavorite = findViewById(R.id.btnAddFavorites);
        btnAddWishList = findViewById(R.id.btnAddWishList);

        bookImage = findViewById(R.id.imgBook);

//        Book book = new Book(1, "1Q84", "Haruki Murakami", 1350, "https://lifestylecollective2017.files.wordpress.com/2020/06/1q84-by-haruki-murakami.jpg",
//                "A work of maddening brilliance", "Long Description");

        Intent intent = getIntent();
        if (null != intent){
            int bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
            if (bookId != -1){
                Book incomingBook = Utils.getInstance(this).getBookById(bookId);
                if (null != incomingBook){
                    setData(incomingBook);

                    handleAlreadyRead(incomingBook);
                    handleWantToReadBooks(incomingBook);
                    handleCurrentlyReadingBooks(incomingBook);
                    handleFavoriteBooks(incomingBook);
                }
            }
        }

    }

    private void handleFavoriteBooks(final Book book){
        ArrayList<Book> favoriteBooks = Utils.getInstance(this).getFavoriteBooks();

        boolean existInFavoriteBooks = false;

        for(Book b: favoriteBooks){
            if (b.getId() == book.getId()){
                existInFavoriteBooks = true;
            }
        }

        if (existInFavoriteBooks){
            btnAddToFavorite.setEnabled(false);
        } else {
            btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Utils.getInstance(BookActivity.this).addToFavorites(book)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, FavoritesActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private void handleCurrentlyReadingBooks(final Book book){
        ArrayList<Book> currentlyReadingBooks = Utils.getInstance(this).getCurrentlyReadingBooks();

        boolean existInCurrentlyReadingBooks = false;

        for(Book b: currentlyReadingBooks){
            if (b.getId() == book.getId()){
                existInCurrentlyReadingBooks = true;
            }
        }

        if (existInCurrentlyReadingBooks){
            btnAddToCurrentlyReading.setEnabled(false);
        } else {
            btnAddToCurrentlyReading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Utils.getInstance(BookActivity.this).addToCurrentlyReading(book)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, CurrentlyReadingActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleWantToReadBooks(final Book book) {
        ArrayList<Book> wantToReadBooks = Utils.getInstance(this).getWantToReadBooks();

        boolean existInWantToReadBooks = false;

        for(Book b: wantToReadBooks){
            if (b.getId() == book.getId()){
                existInWantToReadBooks = true;
            }
        }

        if (existInWantToReadBooks){
            btnAddWishList.setEnabled(false);
        } else {
            btnAddWishList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Utils.getInstance(BookActivity.this).addToWantToRead(book)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, WantToReadActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleAlreadyRead(final Book book){
        ArrayList<Book> alreadyReadBooks = Utils.getInstance(this).getAlreadyReadBooks();

        boolean existInAlreadyReadBooks = false;

        for(Book b: alreadyReadBooks){
            if (b.getId() == book.getId()){
                existInAlreadyReadBooks = true;
            }
        }

        if (existInAlreadyReadBooks){
            btnAddToAlreadyRead.setEnabled(false);
        } else {
            btnAddToAlreadyRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Utils.getInstance(BookActivity.this).addToAlreadyRead(book)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, AlreadyReadBookActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setData(Book book){
        txtBookName.setText(book.getName());
        txtAuthor.setText(book.getAuthor());
        txtPages.setText(String.valueOf(book.getPages()));
        txtDescripition.setText(book.getLongDesc());
        Glide.with(this)
                .asBitmap().load(book.getImageUrl())
                .into(bookImage);
    }
}