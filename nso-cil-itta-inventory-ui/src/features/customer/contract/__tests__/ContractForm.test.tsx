import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import { describe, expect, it, vi } from 'vitest';
import { ContractForm } from '../ContractForm';
import { contractFixture } from './fixtures';

describe('ContractForm', () => {
  it('renders every field', () => {
    render(<ContractForm onSubmit={vi.fn()} />);
    expect(screen.getByRole('form', { name: 'Contract form' })).toBeInTheDocument();
  });

  it('shows validation errors and blocks submit when empty', async () => {
    const onSubmit = vi.fn();
    render(<ContractForm onSubmit={onSubmit} />);
    fireEvent.click(screen.getByRole('button', { name: 'Save' }));
    await waitFor(() => expect(screen.getAllByText(/is required/).length).toBeGreaterThan(0));
    expect(onSubmit).not.toHaveBeenCalled();
  });

  it('submits cleaned values for an existing record', async () => {
    const onSubmit = vi.fn();
    render(<ContractForm initial={contractFixture} onSubmit={onSubmit} submitLabel="Save changes" />);
    fireEvent.click(screen.getByRole('button', { name: 'Save changes' }));
    await waitFor(() => expect(onSubmit).toHaveBeenCalledTimes(1));
    expect(onSubmit.mock.calls[0][0].code).toBe(contractFixture.code);
  });

  it('disables the submit button while busy', () => {
    render(<ContractForm onSubmit={vi.fn()} busy />);
    expect(screen.getByRole('button', { name: /Saving/ })).toBeDisabled();
  });

  it('shows the server error', () => {
    render(<ContractForm onSubmit={vi.fn()} serverError="Duplicate code" />);
    expect(screen.getByRole('alert')).toHaveTextContent('Duplicate code');
  });

  it('calls onCancel', () => {
    const onCancel = vi.fn();
    render(<ContractForm onSubmit={vi.fn()} onCancel={onCancel} />);
    fireEvent.click(screen.getByRole('button', { name: 'Cancel' }));
    expect(onCancel).toHaveBeenCalled();
  });
});
