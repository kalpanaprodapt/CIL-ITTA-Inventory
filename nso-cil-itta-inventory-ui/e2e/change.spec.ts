import { expect, test } from '@playwright/test';

test.describe('Change and Work Management', () => {
  test('Change Request list renders', async ({ page }) => {
    await page.goto('/change/change-requests');
    await expect(page.getByRole('heading', { name: 'Change Requests' }).or(page.getByRole('heading', { name: 'Change Requestes' }))).toBeVisible();
  });

  test('Change Request create form is reachable', async ({ page }) => {
    await page.goto('/change/change-requests/new');
    await expect(page.getByRole('form', { name: 'Change Request form' })).toBeVisible();
  });

  test('Maintenance Window list renders', async ({ page }) => {
    await page.goto('/change/maintenance-windows');
    await expect(page.getByRole('heading', { name: 'Maintenance Windows' }).or(page.getByRole('heading', { name: 'Maintenance Windowes' }))).toBeVisible();
  });

  test('Maintenance Window create form is reachable', async ({ page }) => {
    await page.goto('/change/maintenance-windows/new');
    await expect(page.getByRole('form', { name: 'Maintenance Window form' })).toBeVisible();
  });

  test('Work Order list renders', async ({ page }) => {
    await page.goto('/change/work-orders');
    await expect(page.getByRole('heading', { name: 'Work Orders' }).or(page.getByRole('heading', { name: 'Work Orderes' }))).toBeVisible();
  });

  test('Work Order create form is reachable', async ({ page }) => {
    await page.goto('/change/work-orders/new');
    await expect(page.getByRole('form', { name: 'Work Order form' })).toBeVisible();
  });

  test('Field Task list renders', async ({ page }) => {
    await page.goto('/change/field-tasks');
    await expect(page.getByRole('heading', { name: 'Field Tasks' }).or(page.getByRole('heading', { name: 'Field Taskes' }))).toBeVisible();
  });

  test('Field Task create form is reachable', async ({ page }) => {
    await page.goto('/change/field-tasks/new');
    await expect(page.getByRole('form', { name: 'Field Task form' })).toBeVisible();
  });

  test('Technician list renders', async ({ page }) => {
    await page.goto('/change/technicians');
    await expect(page.getByRole('heading', { name: 'Technicians' }).or(page.getByRole('heading', { name: 'Technicianes' }))).toBeVisible();
  });

  test('Technician create form is reachable', async ({ page }) => {
    await page.goto('/change/technicians/new');
    await expect(page.getByRole('form', { name: 'Technician form' })).toBeVisible();
  });
});
