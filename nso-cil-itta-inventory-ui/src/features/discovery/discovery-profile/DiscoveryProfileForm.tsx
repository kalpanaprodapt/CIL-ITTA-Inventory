import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { DISCOVERY_PROFILE_PROTOCOL_OPTIONS, DiscoveryProfileProtocol } from './types';
import { validateDiscoveryProfile, cleanDiscoveryProfileValues } from './schema';
import { DISCOVERYPROFILE_DEFAULTS } from './types';
import type { DiscoveryProfileFormValues, DiscoveryProfileInput } from './types';

interface Props {
  initial?: DiscoveryProfileFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: DiscoveryProfileInput) => void;
  onCancel?: () => void;
}

export function DiscoveryProfileForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<DiscoveryProfileFormValues>({ ...DISCOVERYPROFILE_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof DiscoveryProfileFormValues>(key: K, value: DiscoveryProfileFormValues[K]) {
    setValues((prev) => ({ ...prev, [key]: value }));
    if (errors[key as string]) {
      setErrors((prev) => {
        const next = { ...prev };
        delete next[key as string];
        return next;
      });
    }
  }

  function submit(ev: React.FormEvent) {
    ev.preventDefault();
    const result = validateDiscoveryProfile(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanDiscoveryProfileValues(values as Record<string, unknown>) as unknown as DiscoveryProfileInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Discovery Profile form">
      {serverError ? <div role="alert" className="form-error">{serverError}</div> : null}
      <FormField label="Code" htmlFor="code" error={errors.code} required={true}>
        <Input
          id="code"
          value={values.code ?? ''}
          maxLength={255}
          onChange={(v) => set('code', v)}
        />
      </FormField>
      <FormField label="Name" htmlFor="name" error={errors.name} required={true}>
        <Input
          id="name"
          value={values.name ?? ''}
          maxLength={255}
          onChange={(v) => set('name', v)}
        />
      </FormField>
      <FormField label="Description" htmlFor="description" error={errors.description} required={false}>
        <TextArea
          id="description"
          value={values.description ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('description', v)}
        />
      </FormField>
      <FormField label="Status" htmlFor="status" error={errors.status} required={true}>
        <Select
          id="status"
          value={values.status ?? ''}
          options={LIFECYCLE_OPTIONS}
          allowEmpty={false}
          onChange={(v) => set('status', (v || undefined) as LifecycleStatus | undefined)}
        />
      </FormField>
      <FormField label="External Ref" htmlFor="externalRef" error={errors.externalRef} required={false}>
        <Input
          id="externalRef"
          value={values.externalRef ?? ''}
          maxLength={255}
          onChange={(v) => set('externalRef', v)}
        />
      </FormField>
      <FormField label="Profile Name" htmlFor="profileName" error={errors.profileName} required={true}>
        <Input
          id="profileName"
          value={values.profileName ?? ''}
          maxLength={255}
          onChange={(v) => set('profileName', v)}
        />
      </FormField>
      <FormField label="Protocol" htmlFor="protocol" error={errors.protocol} required={false}>
        <Select
          id="protocol"
          value={values.protocol ?? ''}
          options={DISCOVERY_PROFILE_PROTOCOL_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('protocol', (v || undefined) as DiscoveryProfileProtocol | undefined)}
        />
      </FormField>
      <FormField label="Credential Ref" htmlFor="credentialRef" error={errors.credentialRef} required={false}>
        <Input
          id="credentialRef"
          value={values.credentialRef ?? ''}
          maxLength={255}
          onChange={(v) => set('credentialRef', v)}
        />
      </FormField>
      <FormField label="Scope Cidr" htmlFor="scopeCidr" error={errors.scopeCidr} required={false}>
        <Input
          id="scopeCidr"
          value={values.scopeCidr ?? ''}
          maxLength={255}
          onChange={(v) => set('scopeCidr', v)}
        />
      </FormField>
      <FormField label="Schedule Cron" htmlFor="scheduleCron" error={errors.scheduleCron} required={false}>
        <Input
          id="scheduleCron"
          value={values.scheduleCron ?? ''}
          maxLength={255}
          onChange={(v) => set('scheduleCron', v)}
        />
      </FormField>
      <FormField label="Cost Center" htmlFor="costCenter" error={errors.costCenter} required={false}>
        <Input
          id="costCenter"
          value={values.costCenter ?? ''}
          maxLength={255}
          onChange={(v) => set('costCenter', v)}
        />
      </FormField>
      <FormField label="Notes" htmlFor="notes" error={errors.notes} required={false}>
        <TextArea
          id="notes"
          value={values.notes ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('notes', v)}
        />
      </FormField>
      <FormField label="Effective From" htmlFor="effectiveFrom" error={errors.effectiveFrom} required={false}>
        <DateInput
          id="effectiveFrom"
          value={values.effectiveFrom ?? ''}
          onChange={(v) => set('effectiveFrom', v)}
        />
      </FormField>
      <FormField label="Effective To" htmlFor="effectiveTo" error={errors.effectiveTo} required={false}>
        <DateInput
          id="effectiveTo"
          value={values.effectiveTo ?? ''}
          onChange={(v) => set('effectiveTo', v)}
        />
      </FormField>
      <FormField label="Labels" htmlFor="labels" error={errors.labels} required={false}>
        <Input
          id="labels"
          value={values.labels ?? ''}
          maxLength={255}
          onChange={(v) => set('labels', v)}
        />
      </FormField>
      <div className="entity-form__actions">
        {onCancel ? (
          <Button type="button" variant="ghost" onClick={onCancel}>
            Cancel
          </Button>
        ) : null}
        <Button type="submit" disabled={busy}>
          {busy ? 'Saving…' : submitLabel}
        </Button>
      </div>
    </form>
  );
}
