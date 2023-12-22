import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { toastConfig } from './toast-config';

@Injectable({
  providedIn: 'root'
})
export class ToastMessageService {

  constructor(private toastr: ToastrService) {
    this.toastr.toastrConfig.preventDuplicates = true;
  }

  success(message: string, title?: string) {
    this.toastr.success(message, title, toastConfig);
  }

  error(message: string, title?: string) {
    this.toastr.error(message, title, toastConfig);
  }

  warning(message: string, title?: string) {
    this.toastr.warning(message, title, toastConfig);
  }

  info(message: string, title?: string) {
    this.toastr.info(message, title, toastConfig);
  }

}