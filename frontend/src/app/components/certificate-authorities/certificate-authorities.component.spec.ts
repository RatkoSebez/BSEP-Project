import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CertificateAuthoritiesComponent } from './certificate-authorities.component';

describe('CertificateAuthoritiesComponent', () => {
  let component: CertificateAuthoritiesComponent;
  let fixture: ComponentFixture<CertificateAuthoritiesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CertificateAuthoritiesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CertificateAuthoritiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
