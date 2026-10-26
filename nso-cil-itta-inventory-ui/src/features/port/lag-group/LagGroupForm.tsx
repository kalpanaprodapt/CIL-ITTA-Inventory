import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { LAG_GROUP_LACP_MODE_OPTIONS, LAG_GROUP_LIFECYCLE_STAGE_OPTIONS, LagGroupLacpMode, LagGroupLifecycleStage } from './types';
import { validateLagGroup, cleanLagGroupValues } from './schema';
import { LAGGROUP_DEFAULTS } from './types';
import type { LagGroupFormValues, LagGroupInput } from './types';

interface Props {
  initial?: LagGroupFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: LagGroupInput) => void;
  onCancel?: () => void;
}

export function LagGroupForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<LagGroupFormValues>({ ...LAGGROUP_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof LagGroupFormValues>(key: K, value: LagGroupFormValues[K]) {
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
    const result = validateLagGroup(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanLagGroupValues(values as Record<string, unknown>) as unknown as LagGroupInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Lag Group form">
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
      <FormField label="Device Id" htmlFor="deviceId" error={errors.deviceId} required={true}>
        <Input
          id="deviceId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.deviceId ?? ''}
          onChange={(v) => set('deviceId', v)}
        />
      </FormField>
      <FormField label="Lag Name" htmlFor="lagName" error={errors.lagName} required={true}>
        <Input
          id="lagName"
          value={values.lagName ?? ''}
          maxLength={255}
          onChange={(v) => set('lagName', v)}
        />
      </FormField>
      <FormField label="Member Count" htmlFor="memberCount" error={errors.memberCount} required={false}>
        <NumberInput
          id="memberCount"
          value={values.memberCount}
          step={1}
          onChange={(v) => set('memberCount', v)}
        />
      </FormField>
      <FormField label="Lacp Mode" htmlFor="lacpMode" error={errors.lacpMode} required={false}>
        <Select
          id="lacpMode"
          value={values.lacpMode ?? ''}
          options={LAG_GROUP_LACP_MODE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lacpMode', (v || undefined) as LagGroupLacpMode | undefined)}
        />
      </FormField>
      <FormField label="Min Links" htmlFor="minLinks" error={errors.minLinks} required={false}>
        <NumberInput
          id="minLinks"
          value={values.minLinks}
          step={1}
          onChange={(v) => set('minLinks', v)}
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
          options={LAG_GROUP_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as LagGroupLifecycleStage | undefined)}
        />
      </FormField>
      <FormField label="Source System" htmlFor="sourceSystem" error={errors.sourceSystem} required={false}>
        <Input
          id="sourceSystem"
          value={values.sourceSystem ?? ''}
          maxLength={255}
          onChange={(v) => set('sourceSystem', v)}
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
