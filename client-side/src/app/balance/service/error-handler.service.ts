import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { ToastMessageService } from 'src/app/core/service/toast-message/toast-message.service';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlerService {

  constructor(private toastService: ToastMessageService, private router: Router) { }

  handleHttpError(error: HttpErrorResponse) {
    if (error.status === 404) {
      this.router.navigate(['/wallet']);
      this.toastService.error('Data not found', 'Error');
      return throwError(() => new Error("Data not found"));
    } else if (error.status === 400) {
      this.router.navigate(['/wallet']);
      this.toastService.info('Please provide correct wallets data. Check wallet address syntax.', 'Info');
      return throwError(() => new Error(this.errorMessage(error)));
    } else if (error.status === 500) {
      this.router.navigate(['/wallet']);
      this.toastService.error('Internal system error. Please try again later.', 'Error');
      return throwError(() => new Error(this.errorMessage(error)));
    } else {
      this.router.navigate(['/wallet']);
      this.toastService.error('System error occurred. Please try again later.', 'Error');
      return throwError(() => new Error(this.errorMessage(error)));
    }
  }

  private errorMessage(error: any): string {
    let errorMessage = '';
    if (error.error && error.error.message) {
      errorMessage = error.error.message;
    } else {
      errorMessage = 'An error occurred: ' + error.message;
    }
    return errorMessage;
  }
}
