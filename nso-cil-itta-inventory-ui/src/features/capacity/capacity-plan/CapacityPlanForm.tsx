import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { CAPACITY_PLAN_LIFECYCLE_STAGE_OPTIONS, CapacityPlanLifecycleStage } from './types';
import { validateCapacityPlan, cleanCapacityPlanValues } from './schema';
import { CAPACITYPLAN_DEFAULTS } from './types';
import type { CapacityPlanFormValues, CapacityPlanInput } from './types';

interface Props {
  initial?: CapacityPlanFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: CapacityPlanInput) => void;
  onCancel?: () => void;
}

export function CapacityPlanForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<CapacityPlanFormValues>({ ...CAPACITYPLAN_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof CapacityPlanFormValues>(key: K, value: CapacityPlanFormValues[K]) {
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
    const result = validateCapacityPlan(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanCapacityPlanValues(values as Record<string, unknown>) as unknown as CapacityPlanInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Capacity Plan form">
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
      <FormField label="Plan Name" htmlFor="planName" error={errors.planName} required={true}>
        <Input
          id="planName"
          value={values.planName ?? ''}
          maxLength={255}
          onChange={(v) => set('planName', v)}
        />
      </FormField>
      <FormField label="Horizon Months" htmlFor="horizonMonths" error={errors.horizonMonths} required={true}>
        <NumberInput
          id="horizonMonths"
          value={values.horizonMonths}
          step={1}
          onChange={(v) => set('horizonMonths', v)}
        />
      </FormField>
      <FormField label="Target Utilization Pct" htmlFor="targetUtilizationPct" error={errors.targetUtilizationPct} required={false}>
        <NumberInput
          id="targetUtilizationPct"
          value={values.targetUtilizationPct}
          step={0.0001}
          onChange={(v) => set('targetUtilizationPct', v)}
        />
      </FormField>
      <FormField label="Owner" htmlFor="owner" error={errors.owner} required={false}>
        <Input
          id="owner"
          value={values.owner ?? ''}
          maxLength={255}
          onChange={(v) => set('owner', v)}
        />
      </FormField>
      <FormField label="Approved On" htmlFor="approvedOn" error={errors.approvedOn} required={false}>
        <DateInput
          id="approvedOn"
          value={values.approvedOn ?? ''}
          onChange={(v) => set('approvedOn', v)}
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
          options={CAPACITY_PLAN_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as CapacityPlanLifecycleStage | undefined)}
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
      <FormField label="Region" htmlFor="region" error={errors.region} required={false}>
        <Input
          id="region"
          value={values.region ?? ''}
          maxLength={255}
          onChange={(v) => set('region', v)}
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
