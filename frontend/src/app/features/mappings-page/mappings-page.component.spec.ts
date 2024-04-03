import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MappingsPageComponent } from './mappings-page.component';

describe('MappingsPageComponent', () => {
  let component: MappingsPageComponent;
  let fixture: ComponentFixture<MappingsPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MappingsPageComponent]
    });
    fixture = TestBed.createComponent(MappingsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
