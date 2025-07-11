import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { AuthService } from '../../services/auth';
import { BookService, Book } from '../../services/book';
import { Router } from '@angular/router';

@Component({
  selector: 'app-book-list',
  standalone: false,
  templateUrl: './book-list.html',
  styleUrl: './book-list.css'
})
export class BookList implements OnInit {
  books: Book[] = [];
  allBooks: Book[] = []; // Store all books for filtering
  loading = false;
  error = '';
  searchQuery = '';
  isSearching = false;

  constructor(
    private bookService: BookService,
    private authService: AuthService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    console.log('BookList component initialized');
    console.log('Is user logged in?', this.authService.isLoggedIn());
    console.log('Current token:', this.authService.getToken());
    
    if (!this.authService.isLoggedIn()) {
      console.log('User not logged in, redirecting to login');
      this.router.navigate(['/login']);
      return;
    }
    
    this.loadBooks();
  }

  loadBooks() {
    console.log('BookList: Starting to load books...');
    this.loading = true;
    this.error = '';
    
    this.bookService.getAllBooks().subscribe({
      next: (books: Book[]) => {
        console.log('BookList: Books loaded successfully:', books);
        console.log('BookList: Number of books received:', books.length);
        this.books = books;
        this.allBooks = [...books]; // Store all books
        this.loading = false;
        this.cdr.detectChanges(); // Force change detection
        console.log('BookList: Component state - loading:', this.loading, 'error:', this.error, 'books count:', this.books.length);
      },
      error: (err: any) => {
        console.error('BookList: Error loading books:', err);
        this.error = 'Failed to load books';
        this.loading = false;
        this.cdr.detectChanges(); // Force change detection
        console.log('BookList: Error state - loading:', this.loading, 'error:', this.error);
      }
    });
  }

  deleteBook(isbn: number) {
    if (confirm('Are you sure you want to delete this book?')) {
      console.log('Deleting book with ISBN:', isbn);
      this.bookService.deleteBook(isbn).subscribe({
        next: (response) => {
          console.log('Book deleted successfully:', response);
          // Clear any existing error
          this.error = '';
          // Reload the list to refresh the display
          this.loadBooks();
        },
        error: (err: any) => {
          console.error('Error deleting book:', err);
          this.error = 'Failed to delete book';
          this.cdr.detectChanges();
        }
      });
    }
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  // Search functionality
  onSearchChange(query: string) {
    this.searchQuery = query.trim();
    
    if (this.searchQuery === '') {
      // Show all books if search is empty
      this.books = [...this.allBooks];
      this.isSearching = false;
    } else {
      // Filter books locally by title or author
      this.isSearching = true;
      this.books = this.allBooks.filter(book => 
        book.title.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        book.author.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        book.isbn.toString().includes(this.searchQuery)
      );
    }
    this.cdr.detectChanges();
  }

  clearSearch() {
    this.searchQuery = '';
    this.books = [...this.allBooks];
    this.isSearching = false;
    this.cdr.detectChanges();
  }

  // Alternative: Server-side search (if your API supports it)
  searchBooksOnServer(query: string) {
    if (query.trim() === '') {
      this.loadBooks();
      return;
    }

    this.loading = true;
    this.error = '';
    this.isSearching = true;
    
    this.bookService.searchBooks(query).subscribe({
      next: (books: Book[]) => {
        console.log('Search results:', books);
        this.books = books;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err: any) => {
        console.error('Search error:', err);
        this.error = 'Failed to search books';
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }
}
