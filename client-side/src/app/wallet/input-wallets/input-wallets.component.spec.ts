import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InputWalletsComponent } from './input-wallets.component';

describe('InputWalletsComponent', () => {
  let component: InputWalletsComponent;
  let fixture: ComponentFixture<InputWalletsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InputWalletsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InputWalletsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
