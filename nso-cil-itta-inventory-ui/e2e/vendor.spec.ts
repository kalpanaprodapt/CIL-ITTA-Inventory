import { expect, test } from '@playwright/test';

test.describe('Vendor and Model', () => {
  test('Vendor list renders', async ({ page }) => {
    await page.goto('/vendor/vendors');
    await expect(page.getByRole('heading', { name: 'Vendors' }).or(page.getByRole('heading', { name: 'Vendores' }))).toBeVisible();
  });

  test('Vendor create form is reachable', async ({ page }) => {
    await page.goto('/vendor/vendors/new');
    await expect(page.getByRole('form', { name: 'Vendor form' })).toBeVisible();
  });

  test('Hardware Model list renders', async ({ page }) => {
    await page.goto('/vendor/hardware-models');
    await expect(page.getByRole('heading', { name: 'Hardware Models' }).or(page.getByRole('heading', { name: 'Hardware Modeles' }))).toBeVisible();
  });

  test('Hardware Model create form is reachable', async ({ page }) => {
    await page.goto('/vendor/hardware-models/new');
    await expect(page.getByRole('form', { name: 'Hardware Model form' })).toBeVisible();
  });

  test('Sku list renders', async ({ page }) => {
    await page.goto('/vendor/skus');
    await expect(page.getByRole('heading', { name: 'Skus' }).or(page.getByRole('heading', { name: 'Skues' }))).toBeVisible();
  });

  test('Sku create form is reachable', async ({ page }) => {
    await page.goto('/vendor/skus/new');
    await expect(page.getByRole('form', { name: 'Sku form' })).toBeVisible();
  });

  test('Support Contract list renders', async ({ page }) => {
    await page.goto('/vendor/support-contracts');
    await expect(page.getByRole('heading', { name: 'Support Contracts' }).or(page.getByRole('heading', { name: 'Support Contractes' }))).toBeVisible();
  });

  test('Support Contract create form is reachable', async ({ page }) => {
    await page.goto('/vendor/support-contracts/new');
    await expect(page.getByRole('form', { name: 'Support Contract form' })).toBeVisible();
  });

  test('Warranty list renders', async ({ page }) => {
    await page.goto('/vendor/warranties');
    await expect(page.getByRole('heading', { name: 'Warrantys' }).or(page.getByRole('heading', { name: 'Warrantyes' }))).toBeVisible();
  });

  test('Warranty create form is reachable', async ({ page }) => {
    await page.goto('/vendor/warranties/new');
    await expect(page.getByRole('form', { name: 'Warranty form' })).toBeVisible();
  });
});
