import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { LICENSE_KEY_LIFECYCLE_STAGE_OPTIONS, LicenseKeyLifecycleStage } from './types';
import { validateLicenseKey, cleanLicenseKeyValues } from './schema';
import { LICENSEKEY_DEFAULTS } from './types';
import type { LicenseKeyFormValues, LicenseKeyInput } from './types';

interface Props {
  initial?: LicenseKeyFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: LicenseKeyInput) => void;
  onCancel?: () => void;
}

export function LicenseKeyForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<LicenseKeyFormValues>({ ...LICENSEKEY_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof LicenseKeyFormValues>(key: K, value: LicenseKeyFormValues[K]) {
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
    const result = validateLicenseKey(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanLicenseKeyValues(values as Record<string, unknown>) as unknown as LicenseKeyInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="License Key form">
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
      <FormField label="Pool Id" htmlFor="poolId" error={errors.poolId} required={true}>
        <Input
          id="poolId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.poolId ?? ''}
          onChange={(v) => set('poolId', v)}
        />
      </FormField>
      <FormField label="Key Value" htmlFor="keyValue" error={errors.keyValue} required={true}>
        <Input
          id="keyValue"
          value={values.keyValue ?? ''}
          maxLength={255}
          onChange={(v) => set('keyValue', v)}
        />
      </FormField>
      <FormField label="Feature" htmlFor="feature" error={errors.feature} required={true}>
        <Input
          id="feature"
          value={values.feature ?? ''}
          maxLength={255}
          onChange={(v) => set('feature', v)}
        />
      </FormField>
      <FormField label="Expires On" htmlFor="expiresOn" error={errors.expiresOn} required={false}>
        <DateInput
          id="expiresOn"
          value={values.expiresOn ?? ''}
          onChange={(v) => set('expiresOn', v)}
        />
      </FormField>
      <FormField label="Assigned Device Id" htmlFor="assignedDeviceId" error={errors.assignedDeviceId} required={false}>
        <Input
          id="assignedDeviceId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.assignedDeviceId ?? ''}
          onChange={(v) => set('assignedDeviceId', v)}
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
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={LICENSE_KEY_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as LicenseKeyLifecycleStage | undefined)}
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
